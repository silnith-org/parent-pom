package org.silnith.timeseriesdata.impl;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.NavigableMap;
import java.util.TreeMap;


/**
 * An event counter that keeps individual counters for each time unit in the
 * temporal range.
 */
public class TimeRangeCounter implements EventCounter {

    private final TemporalField range;
    private final ZonedDateTime start;
    private final ZonedDateTime end;
    private final NavigableMap<ZonedDateTime, EventCounter> counts;

    public TimeRangeCounter(final ZonedDateTime zonedDateTime, final TemporalField range,
            final EventCounter.Factory factory) {
        super();
        this.range = range;
        start = zonedDateTime.truncatedTo(this.range.getRangeUnit());
        end = start.plus(this.range.getRangeUnit().getDuration());
        counts = new TreeMap<>();
        final Duration baseUnitDuration = this.range.getBaseUnit().getDuration();
        final ValueRange valueRange = this.range.rangeRefinedBy(zonedDateTime);
        for (long i = valueRange.getMinimum(); i <= valueRange.getMaximum(); i++) {
            final ZonedDateTime unitStart = start.plus(baseUnitDuration.multipliedBy(i));
            final EventCounter unitCounter = factory.getEventCounter(unitStart);
            counts.put(unitCounter.getStart(), unitCounter);
        }
    }

    @Override
    public ZonedDateTime getStart() {
        return start;
    }

    @Override
    public ZonedDateTime getEnd() {
        return end;
    }

    @Override
    public void addEvent(final String event, final ZonedDateTime timestamp) {
        if (start.isAfter(timestamp)) {
            throw new IllegalArgumentException();
        }
        if (!end.isAfter(timestamp)) {
            throw new IllegalArgumentException();
        }
        final ZonedDateTime inBaseUnit = timestamp.truncatedTo(range.getBaseUnit());
        counts.get(inBaseUnit).addEvent(event, timestamp);
    }

    @Override
    public NavigableMap<ZonedDateTime, Long> getEventCounts(final String event, final TemporalUnit granularity,
            final ZonedDateTime start, final ZonedDateTime end) {
        final NavigableMap<ZonedDateTime, Long> result = new TreeMap<>();
        for (final EventCounter eventCount : counts.subMap(start, true, end, true).values()) {
            result.putAll(eventCount.getEventCounts(event, granularity, start, end));
        }
        /*
         * If the requested granularity is greater than or equal to the entire range of
         * data kept by this level, sum all entries.
         */
        final Duration rangeDuration = range.getRangeUnit().getDuration();
        final Duration granularityDuration = granularity.getDuration();
        if (rangeDuration.compareTo(granularityDuration) <= 0) {
            long sum = 0;
            for (final long l : result.values()) {
                sum += l;
            }
            result.clear();
            result.put(this.start, sum);
        }
        return result;
    }

}
