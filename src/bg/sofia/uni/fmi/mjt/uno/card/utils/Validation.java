package bg.sofia.uni.fmi.mjt.uno.card.utils;

import java.util.Map;

public class Validation {

    private static final String NUMERIC_REGEX = "^\\d+$";

    public static <T extends Number> void checkIndex(T index, Map<Integer, ?> list) {
        if (!list.containsKey(index.intValue())) {
            throw new IllegalArgumentException("The card is not present");
        }
    }

    public static <T> void checkReference(T ref) {
        if (ref == null) {
            throw new IllegalArgumentException("The reference is null");
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches(NUMERIC_REGEX);
    }

}
