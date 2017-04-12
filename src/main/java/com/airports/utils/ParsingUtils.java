package com.airports.utils;

public final class ParsingUtils {
    private ParsingUtils() {
    }

    /**
     * Silently parse a string into an integer.
     *
     * @param value string to parse.
     * @return integer value or null if string can't be parsed.
     */
    public static Integer tryParseInteger(String value) {
        Integer result = null;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

    /**
     * Silently parse a string into a double.
     *
     * @param value string to parse.
     * @return double value or null if string can't be parsed.
     */
    public static Double tryParseDouble(String value) {
        Double result = null;
        try {
            result = Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }
}
