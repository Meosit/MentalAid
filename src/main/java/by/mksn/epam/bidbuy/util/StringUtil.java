package by.mksn.epam.bidbuy.util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean isNullOrEmpty(String string) {
        return (string == null) || (string.isEmpty());
    }

    public static boolean nullableEquals(String stringOne, String stringTwo) {
        boolean result;
        if (stringOne != null) {
            result = stringOne.equals(stringTwo);
        } else {
            result = null == stringTwo;
        }
        return result;
    }

    public static boolean isNull(String string) {
        return string == null;
    }

}
