package by.mksn.epam.bidbuy.command.impl.manager;

import java.util.ResourceBundle;

/**
 * Class which provides handy access to jsp paths properties
 */
public class PathManager {

    /**
     * Path of index page (web app entry point)
     */
    public static final String INDEX = "page.index";
    /**
     * Path of error page
     */
    public static final String ERROR = "page.error";
    /**
     * Path application home page, to this page user forwarded at first
     */
    public static final String HOME = "page.home";
    /**
     * Path of page with login form
     */
    public static final String LOGIN = "page.login";
    /**
     * Path of page with register form
     */
    public static final String REGISTER = "page.register";

    private static final String RESOURCE_NAME = "path";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);

    private PathManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }


}
