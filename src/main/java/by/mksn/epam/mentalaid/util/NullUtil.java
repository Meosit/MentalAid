package by.mksn.epam.mentalaid.util;

/**
 * Util class for different null operations and validations
 */
public final class NullUtil {

    private NullUtil() {
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
    public static <T> boolean nullableEquals(T objectOne, T objectTwo) {
        return (objectOne == null) ? (objectTwo == null) : (objectOne.equals(objectTwo));
    }

    /**
     * Calculates hashCode of object with possible null value.
     *
     * @param object object to calculate hashCode
     * @return {@code object.hashCode()} if object is not null, {@code 0} otherwise
     */
    public static <T> int nullableHashCode(T object) {
        return (object != null) ? object.hashCode() : 0;
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

    /**
     * Checks object for null and returns default value if this object is null. <br>
     * This method equals to the following code snippet: <br>
     * {@code (object == null) ? (defaultValue) : (object)}
     *
     * @param object       object to test
     * @param defaultValue will be returned if object is null
     * @return object if it not null, defaultValue otherwise
     */
    public static <T> T ifNullDefault(T object, T defaultValue) {
        return (object == null) ? defaultValue : object;
    }

}
