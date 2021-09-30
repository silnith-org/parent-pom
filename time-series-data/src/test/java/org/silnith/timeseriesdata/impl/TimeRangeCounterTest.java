package org.silnith.timeseriesdata.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TimeRangeCounterTest {

    EventCounter.Factory factory;

    EventCounter counter;

    @BeforeEach
    void setUp() throws Exception {
        factory = instant -> new LeafCounter(instant, ChronoUnit.HOURS);
        counter = new TimeRangeCounter(
                ZonedDateTime.parse("2012-09-30T02:56:04.001Z"),
                ChronoField.HOUR_OF_DAY,
                factory);
    }

    @Test
    void testGetStart() {
        assertEquals(ZonedDateTime.parse("2012-09-30T00:00:00.000Z"), counter.getStart());
    }

    @Test
    void testGetEnd() {
        assertEquals(ZonedDateTime.parse("2012-10-01T00:00:00.000Z"), counter.getEnd());
    }

    @Test
    void testAddEvent() {
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:56:00.000Z"));
    }

    @Test
    void testGetEventCountsGrouped() {
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("b", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("c", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T04:56:45.000Z"));
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T17:56:32.125Z"));
        
        final NavigableMap<ZonedDateTime, Long> eventCounts = counter.getEventCounts("a", ChronoUnit.DAYS, ZonedDateTime.parse("2012-09-30T00:00:00.000Z"), ZonedDateTime.parse("2012-10-01T00:00:00.000Z"));
        final Map<ZonedDateTime, Long> expected = Collections.singletonMap(ZonedDateTime.parse("2012-09-30T00:00:00.000Z"), 3L);
        assertEquals(expected, eventCounts);
    }

    @Test
    void testGetEventCountsIndividual() {
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("b", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("c", ZonedDateTime.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T04:56:45.000Z"));
        counter.addEvent("a", ZonedDateTime.parse("2012-09-30T17:56:32.125Z"));
        
        final NavigableMap<ZonedDateTime, Long> eventCounts = counter.getEventCounts("a", ChronoUnit.HOURS, ZonedDateTime.parse("2012-09-30T00:00:00.000Z"), ZonedDateTime.parse("2012-10-01T00:00:00.000Z"));
        final Map<ZonedDateTime, Long> expected = new TreeMap<>();
        expected.put(ZonedDateTime.parse("2012-09-30T02:00:00.000Z"), 1L);
        expected.put(ZonedDateTime.parse("2012-09-30T04:00:00.000Z"), 1L);
        expected.put(ZonedDateTime.parse("2012-09-30T17:00:00.000Z"), 1L);
        assertEquals(expected, eventCounts);
    }

}
