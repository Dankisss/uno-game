package bg.sofia.uni.fmi.mjt.uno.card.utils;

import java.util.List;

public class Validation {

    public static <T extends Number> void checkIndex(T index, List<?> list) {
        int indexValue = index.intValue();

        if (indexValue < 0 || list.size() <= indexValue) {
            throw new ;
        }
    }
}
