package org.silnith.timeseriesdata;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.NavigableMap;

public interface TimeSeriesData {

    void addEvent(String event, Instant timestamp);

    NavigableMap<Instant, Long> getEventCounts(String event, TemporalUnit granularity, Instant start, Instant end);

}
