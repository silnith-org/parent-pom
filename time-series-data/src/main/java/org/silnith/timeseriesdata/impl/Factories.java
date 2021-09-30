package org.silnith.timeseriesdata.impl;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;


public class Factories {

    public EventCounter.Factory getCounterForSecond() {
        return new EventCounter.Factory() {

            @Override
            public EventCounter getEventCounter(final ZonedDateTime zonedDateTime) {
                return new LeafCounter(zonedDateTime, ChronoUnit.SECONDS);
            }

        };
    }

    public EventCounter.Factory getCounterForMinute() {
        return new EventCounter.Factory() {

            @Override
            public EventCounter getEventCounter(final ZonedDateTime zonedDateTime) {
                return new TimeRangeCounter(zonedDateTime, ChronoField.SECOND_OF_MINUTE, getCounterForSecond());
            }

        };
    }

    public EventCounter.Factory getCounterForHour() {
        return new EventCounter.Factory() {

            @Override
            public EventCounter getEventCounter(final ZonedDateTime zonedDateTime) {
                return new TimeRangeCounter(zonedDateTime, ChronoField.MINUTE_OF_HOUR, getCounterForMinute());
            }

        };
    }

    public EventCounter.Factory getCounterForDay() {
        return new EventCounter.Factory() {

            @Override
            public EventCounter getEventCounter(final ZonedDateTime zonedDateTime) {
                return new TimeRangeCounter(zonedDateTime, ChronoField.HOUR_OF_DAY, getCounterForHour());
            }

        };
    }

}
