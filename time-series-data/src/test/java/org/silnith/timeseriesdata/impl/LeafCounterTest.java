package org.silnith.timeseriesdata.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


class LeafCounterTest {

    EventCounter counter;

    @BeforeEach
    void setUp() throws Exception {
        counter = new LeafCounter(ZonedDateTime.parse("2012-09-30T02:56:04.001Z"), ChronoUnit.MINUTES);
    }

    @Test
    void testGetStart() {
        assertEquals(ZonedDateTime.parse("2012-09-30T02:56:00.000Z"), counter.getStart());
    }

    @Test
    void testGetEnd() {
        assertEquals(ZonedDateTime.parse("2012-09-30T02:57:00.000Z"), counter.getEnd());
    }

    @Test
    void testAddEventBefore() {
        assertThrows(IllegalArgumentException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:55:59.999Z"));
            }

        });
    }

    @Test
    void testAddEventAtStart() {
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:56:00.000Z"));
    }

    @Test
    void testAddEventAtEnd() {
        assertThrows(IllegalArgumentException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:57:00.000Z"));
            }

        });
    }

    @Test
    void testAddEventAfter() {
        assertThrows(IllegalArgumentException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:57:00.001Z"));
            }

        });
    }

    @Test
    void testGetEventCounts() {
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("b", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("c", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:56:45.000Z"));
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:56:32.125Z"));

        final NavigableMap<ZonedDateTime, Long> eventCounts = counter.getEventCounts("a", ChronoUnit.MINUTES,
                ZonedDateTime.parse("2012-09-30T00:00:00.000Z"), ZonedDateTime.parse("2012-10-01T00:00:00.000Z"));
        final Map<ZonedDateTime, Long> expected = Collections
                .singletonMap(ZonedDateTime.parse("2012-09-30T02:56:00.000Z"), 3L);
        assertEquals(expected, eventCounts);
    }

}
