package org.silnith.timeseriesdata.impl;

import java.time.ZonedDateTime;

import org.silnith.timeseriesdata.TimeSeriesData;


public interface EventCounter extends TimeSeriesData {

    @FunctionalInterface
    interface Factory {

        EventCounter getEventCounter(ZonedDateTime timestamp);

    }

    ZonedDateTime getStart();

    ZonedDateTime getEnd();

}
