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
     * Path of question list page, to this page user forwarded at first
     */
    public static final String HOME = "page.home";
    /**
     * Path of user profile page
     */
    public static final String PROFILE = "page.profile";
    /**
     * Path of single question page
     */
    public static final String QUESTION = "page.question";
    /**
     * Path of page with form to create new question
     */
    public static final String NEW_QUESTION = "page.new_question";
    /**
     * Path of page with login form
     */
    public static final String LOGIN = "page.login";
    /**
     * Path of page with register form
     */
    public static final String REGISTER = "page.register";
    /**
     * Path of page with some user settings
     */
    public static final String SETTINGS = "page.settings";
    /**
     * Path of page with user list
     */
    public static final String USER_LIST = "page.user_list";
    /**
     * Jsp page which generates JSON object with redirect url if operation is succeed
     * or with error information otherwise
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
