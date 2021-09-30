package org.silnith.timeseriesdata;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.NavigableMap;


public interface TimeSeriesData {

    void addEvent(String event, ZonedDateTime timestamp);

    NavigableMap<ZonedDateTime, Long> getEventCounts(String event, TemporalUnit granularity, ZonedDateTime start,
            ZonedDateTime end);

}
