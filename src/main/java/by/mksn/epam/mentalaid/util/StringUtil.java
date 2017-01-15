package by.mksn.epam.mentalaid.util;


import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

/**
 * Util class for different string operations
 */
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * Checks is specified string {@code null} or has no significant char
     *
     * @param string string to test
     * @return {@code true} if {@code null} or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String string) {
        return (isNull(string)) || (string.trim().isEmpty());
    }

    /**
     * Truncates string to specified size. If string length
     * lower than specified size, original string will be returned.
     *
     * @param string string to truncate
     * @param size   max size of new string
     * @return truncated string
     */
    public static String truncateToSize(String string, int size) {
        if (isNull(string)) {
            return null;
        } else {
            return (string.length() < size) ? string : string.substring(0, size);
        }
    }

    /**
     * Removes all carriage return ({@code \r}) symbols from the specified string
     *
     * @param string string to perform
     * @return string without {@code \r}
     */
    public static String removeCarriageReturns(String string) {
        return string.replace("\r", "");
    }

    /**
     * Trims specified string and replace all empty line ({@code \n} or {@code \r\n})
     * strokes (more than 2) with single empty line
     *
     * @param string string to perform
     * @return trimmed and collapsed string, {@code null}, if the parameter string was {@code null}
     */
    public static String trimAndCollapseNewLines(String string) {
        return isNull(string) ? null : removeCarriageReturns(string.trim()).replaceAll("(\\n){2,}", "\n\n");
    }

}
