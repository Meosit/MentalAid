package by.mksn.epam.bidbuy.command.manager;

import java.util.ResourceBundle;

/**
 * Class which provides handy access to jsp paths properties
 */
public class PathManager {

    /**
     * Path of index page (web app entry point)
     */
    public static final String INDEX = "page.index";

    private static final String RESOURCE_NAME = "path";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);

    private PathManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }


}
