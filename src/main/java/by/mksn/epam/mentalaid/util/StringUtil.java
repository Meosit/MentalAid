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

    public static String truncateToSize(String string, int size) {
        if (isNull(string)) {
            return null;
        } else {
            return (string.length() < size) ? string : string.substring(0, size);
        }
    }

    /**
     * Trims specified string and replace all empty line ({@code \n})
     * strokes (more than 2) with single empty line
     *
     * @param string string to perform
     * @return trimmed and collapsed string, {@code null}, if the parameter string was {@code null}
     */
    public static String trimAndCollapseNewLines(String string) {
        return isNull(string) ? null : string.trim().replaceAll("(\\n){2,}", "\n\n");
    }
}
