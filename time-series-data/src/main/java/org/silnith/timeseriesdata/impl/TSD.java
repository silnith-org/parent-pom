package org.silnith.timeseriesdata.impl;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.silnith.timeseriesdata.TimeSeriesData;


public class TSD implements TimeSeriesData {

    public static final TSD instance = new TSD(new Factories().getCounterForDay());

    private final EventCounter.Factory factory;
    private final ConcurrentNavigableMap<ZonedDateTime, EventCounter> counts;

    public TSD(final EventCounter.Factory factory) {
        super();
        this.factory = factory;
        counts = new ConcurrentSkipListMap<>();
    }

    public ZonedDateTime getStart() {
        return counts.firstEntry().getValue().getStart();
    }

    public ZonedDateTime getEnd() {
        return counts.lastEntry().getValue().getEnd();
    }

    @Override
    public void addEvent(final String event, final ZonedDateTime timestamp) {
        final EventCounter eventCounter = factory.getEventCounter(timestamp);
        final ZonedDateTime start = eventCounter.getStart();
        counts.putIfAbsent(start, eventCounter);
        /*
         * If the putIfAbsent was a no-op, then this will get the previous
         * event counter rather than the one that was just instantiated.
         */
        counts.get(start).addEvent(event, timestamp);
    }

    @Override
    public NavigableMap<ZonedDateTime, Long> getEventCounts(final String event, final TemporalUnit granularity,
            final ZonedDateTime start, final ZonedDateTime end) {
        final NavigableMap<ZonedDateTime, Long> results = new TreeMap<>();
        for (final EventCounter eventCounter : counts.subMap(start, true, end, true).values()) {
            results.putAll(eventCounter.getEventCounts(event, granularity, start, end));
        }
        return results;
    }

}
