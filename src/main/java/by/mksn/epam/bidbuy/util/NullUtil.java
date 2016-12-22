package by.mksn.epam.bidbuy.util;

/**
 * Util class for different null operations and validations
 */
public final class NullUtil {

    private NullUtil() {
    }

    /**
     * Checks is specified string {@code null} or has zero length
     *
     * @param string string to test
     * @return {@code true} if {@code null} or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String string) {
        return (string == null) || (string.isEmpty());
    }

    /**
     * Checks two objects for equality with possible null values. <br>
     * This method equals to the following code snippet: <br>
     * {@code (objectOne == null) ? (objectTwo == null) : (objectOne.equals(objectTwo))}
     *
     * @param objectOne first object to test
     * @param objectTwo second object to test
     * @return {@code true} if the objectOne equals to objectTwo, {@code false} otherwise
     */
    public static boolean nullableEquals(Object objectOne, Object objectTwo) {
        return (objectOne == null) ? (objectTwo == null) : (objectOne.equals(objectTwo));
    }

    /**
     * Checks object for null
     *
     * @param object object to test
     * @return {@code true} if the given object is null, {@code false} otherwise
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

}
