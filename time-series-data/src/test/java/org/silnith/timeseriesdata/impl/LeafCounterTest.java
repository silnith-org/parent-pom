package org.silnith.timeseriesdata.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class LeafCounterTest {

    EventCounter counter;

    @BeforeEach
    void setUp() throws Exception {
        counter = new LeafCounter(Instant.parse("2012-09-30T02:56:04.001Z"), ChronoUnit.MINUTES);
    }

    @Test
    void testGetStart() {
        assertEquals(Instant.parse("2012-09-30T02:56:00.000Z"), counter.getStart());
    }

    @Test
    void testGetEnd() {
        assertEquals(Instant.parse("2012-09-30T02:57:00.000Z"), counter.getEnd());
    }

    @Test
    void testAddEventBefore() {
        assertThrows(IllegalArgumentException.class, () -> {
            counter.addEvent("a", Instant.parse("2012-09-30T02:55:59.999Z"));
        });
    }

    @Test
    void testAddEventAtStart() {
        counter.addEvent("a", Instant.parse("2012-09-30T02:56:00.000Z"));
    }

    @Test
    void testAddEventAtEnd() {
        assertThrows(IllegalArgumentException.class, () -> {
            counter.addEvent("a", Instant.parse("2012-09-30T02:57:00.000Z"));
        });
    }

    @Test
    void testAddEventAfter() {
        assertThrows(IllegalArgumentException.class, () -> {
            counter.addEvent("a", Instant.parse("2012-09-30T02:57:00.001Z"));
        });
    }

    @Test
    void testGetEventCounts() {
        counter.addEvent("a", Instant.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("b", Instant.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("c", Instant.parse("2012-09-30T02:56:59.999Z"));
        counter.addEvent("a", Instant.parse("2012-09-30T02:56:45.000Z"));
        counter.addEvent("a", Instant.parse("2012-09-30T02:56:32.125Z"));
        
        final NavigableMap<Instant, Long> eventCounts = counter.getEventCounts("a", ChronoUnit.MINUTES, Instant.parse("2012-09-30T00:00:00.000Z"), Instant.parse("2012-10-01T00:00:00.000Z"));
        final Map<Instant, Long> expected = Collections.singletonMap(Instant.parse("2012-09-30T02:56:00.000Z"), 3L);
        assertEquals(expected, eventCounts);
    }

}
