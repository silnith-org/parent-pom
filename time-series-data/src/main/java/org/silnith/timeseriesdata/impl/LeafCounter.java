package org.silnith.timeseriesdata.impl;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * An event counter that sums all events of the same type within the time range.
 * This is used as a leaf node in a tree of event counters.
 */
public class LeafCounter implements EventCounter {

    private final Instant start;
    private final Instant end;
    private final ConcurrentMap<String, Long> counts;

    public LeafCounter(final Instant instant, final TemporalUnit unit) {
        super();
        start = instant.truncatedTo(unit);
        end = start.plus(unit.getDuration());
        counts = new ConcurrentHashMap<>();
    }

    public LeafCounter(final ZonedDateTime zonedDateTime, final TemporalUnit unit) {
        super();
        start = zonedDateTime.truncatedTo(unit).toInstant();
        end = start.plus(unit.getDuration());
        counts = new ConcurrentHashMap<>();
    }

    @Override
    public Instant getStart() {
        return start;
    }

    @Override
    public Instant getEnd() {
        return end;
    }

    @Override
    public void addEvent(final String event, final Instant timestamp) {
        if (start.isAfter(timestamp)) {
            throw new IllegalArgumentException();
        }
        if (!end.isAfter(timestamp)) {
            throw new IllegalArgumentException();
        }
        counts.putIfAbsent(event, 0L);
        final long eventCount = 1;
        boolean replaced;
        do {
            final long oldValue = counts.get(event);
            final long newValue = oldValue + eventCount;
            replaced = counts.replace(event, oldValue, newValue);
        } while (!replaced);
    }

    @Override
    public NavigableMap<Instant, Long> getEventCounts(final String name, final TemporalUnit granularity,
            final Instant start, final Instant end) {
        final Long count = counts.get(name);
        if (count != null && start.isBefore(this.end) && end.isAfter(this.start)) {
            final NavigableMap<Instant, Long> sortedMap = new TreeMap<>();
            sortedMap.put(this.start, count);
            return sortedMap;
        } else {
            return Collections.emptyNavigableMap();
        }
    }

}
