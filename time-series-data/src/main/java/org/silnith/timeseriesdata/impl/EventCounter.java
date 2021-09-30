package org.silnith.timeseriesdata.impl;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.NavigableMap;


public interface EventCounter {

    @FunctionalInterface
    interface Factory {

        EventCounter getEventCounter(Instant timestamp);

    }

    Instant getStart();

    Instant getEnd();

    void addEvent(String event, Instant timestamp);

    NavigableMap<Instant, Long> getEventCounts(String event, TemporalUnit granularity, Instant start, Instant end);

}
