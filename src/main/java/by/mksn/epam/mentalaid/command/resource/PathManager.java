package by.mksn.epam.mentalaid.command.resource;

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
     * Path of application home page, to this page user forwarded at first
     */
    public static final String HOME = "page.home";
    /**
     * Path of page with lot list
     */
    public static final String LOT_LIST = "page.lot_list";
    /**
     * Path of page with login form
     */
    public static final String LOGIN = "page.login";
    /**
     * Path of page with register form
     */
    public static final String REGISTER = "page.register";
    /**
     * Path of page with ajax responce object
     */
    public static final String AJAX_RESPONSE = "page.ajax_response";

    private static final String RESOURCE_NAME = "path";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);

    private PathManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }


}
