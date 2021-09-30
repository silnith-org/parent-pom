package org.silnith.timeseriesdata.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.silnith.timeseriesdata.TimeSeriesData;


public class TSD implements TimeSeriesData {
    
    public static final TSD instance = new TSD(ChronoUnit.DAYS, new Factories().getCounterForDay());

    private final TemporalUnit temporalUnit;
    private final EventCounter.Factory factory;
    private final ConcurrentNavigableMap<Instant, EventCounter> counts;

    public TSD(final TemporalUnit temporalUnit, final EventCounter.Factory factory) {
        super();
        this.temporalUnit = temporalUnit;
        this.factory = factory;
        counts = new ConcurrentSkipListMap<>();
    }

    public Instant getStart() {
        return counts.firstEntry().getValue().getStart();
    }

    public Instant getEnd() {
        return counts.lastEntry().getValue().getEnd();
    }

    @Override
    public void addEvent(final String event, final Instant timestamp) {
        final Instant start = timestamp.truncatedTo(temporalUnit);

        counts.putIfAbsent(start, factory.getEventCounter(timestamp));
        counts.get(start).addEvent(event, timestamp);
    }

    @Override
    public NavigableMap<Instant, Long> getEventCounts(final String event, final TemporalUnit granularity,
            final Instant start, final Instant end) {
        final NavigableMap<Instant, Long> results = new TreeMap<>();
        for (final EventCounter eventCounter : counts.subMap(start, true, end, true).values()) {
            results.putAll(eventCounter.getEventCounts(event, granularity, start, end));
        }
        return results;
    }

}
