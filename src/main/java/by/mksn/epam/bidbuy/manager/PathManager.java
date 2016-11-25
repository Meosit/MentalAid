package by.mksn.epam.bidbuy.manager;

import java.util.ResourceBundle;

public class PathManager {

    public static final String INDEX = "page.index";

    private static final String RESOURCE_NAME = "path";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);

    private PathManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }


}
