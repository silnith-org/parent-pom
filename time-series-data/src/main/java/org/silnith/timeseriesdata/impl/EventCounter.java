package org.silnith.timeseriesdata.impl;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.NavigableMap;


public interface EventCounter {

    @FunctionalInterface
    interface Factory {

        EventCounter getEventCounter(ZonedDateTime timestamp);

    }

    ZonedDateTime getStart();

    ZonedDateTime getEnd();

    void addEvent(String event, ZonedDateTime timestamp);

    NavigableMap<ZonedDateTime, Long> getEventCounts(String event, TemporalUnit granularity, ZonedDateTime start,
            ZonedDateTime end);

}
