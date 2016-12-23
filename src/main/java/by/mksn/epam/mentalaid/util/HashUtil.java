package by.mksn.epam.mentalaid.util;

import org.mindrot.jbcrypt.BCrypt;


public final class HashUtil {

    private static final int HASH_ROUNDS = 12;

    private HashUtil() {
    }

    /**
     * Evaluates hash of the given string
     *
     * @param string for hash
     * @return hash of the given string
     */
    public static String hashString(String string) {
        return BCrypt.hashpw(string, BCrypt.gensalt(HASH_ROUNDS));
    }

    /**
     * Checks is the given string corresponds to the given hash
     *
     * @param testString string to test
     * @param hash       hash to test
     * @return {@code true} if the given string corresponds to the given hash, {@code false} otherwise
     */
    public static boolean isValidHash(String testString, String hash) {
        return BCrypt.checkpw(testString, hash);
    }

}
