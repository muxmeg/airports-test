package com.airports.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParsingUtilsTest {
    @Test
    public void tryParseInteger() throws Exception {
        Integer value = ParsingUtils.tryParseInteger("232134221");
        assertEquals(new Integer(232134221), value);
        value = ParsingUtils.tryParseInteger("100.12");
        assertEquals(null, value);
    }

    @Test
    public void tryParseDouble() throws Exception {
        Double value = ParsingUtils.tryParseDouble("2321342213214");
        assertEquals(new Double(2321342213214d), value);
        value = ParsingUtils.tryParseDouble("100.12");
        assertEquals(new Double(100.12), value);
        value = ParsingUtils.tryParseDouble("asdasd");
        assertEquals(null, value);
    }

}