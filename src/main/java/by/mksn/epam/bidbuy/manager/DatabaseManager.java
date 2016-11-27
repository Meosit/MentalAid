package by.mksn.epam.bidbuy.manager;

import java.util.ResourceBundle;

public class DatabaseManager {

    public static final String DRIVER_NAME = "driver";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String POOL_SIZE = "poolSize";
    public static final String URL = "url";
    public static final String ENCODING = "characterEncoding";
    public static final String AUTORECONNECT = "autoReconnect";
    public static final String USE_UNICODE = "useUnicode";

    private static final String RESOURCE_NAME = "database";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);

    private DatabaseManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
