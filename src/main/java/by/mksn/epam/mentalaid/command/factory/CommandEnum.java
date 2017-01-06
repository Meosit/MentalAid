package by.mksn.epam.mentalaid.command.factory;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.impl.*;

/**
 * Represents full list of commands, used in factory
 */
public enum CommandEnum {
    /**
     * This command provides page with registration form
     */
    GET_REGISTER_PAGE(new GetRegisterPageCommand()),
    /**
     * This command provides page with login form
     */
    GET_LOGIN_PAGE(new GetLoginPageCommand()),
    /**
     * This command provides home page
     */
    GET_HOME_PAGE(new GetHomePageCommand()),
    /**
     * This command provides question page
     */
    GET_QUESTION_PAGE(new GetQuestionPageCommand()),
    /**
     * Changes site content locale and saves it in database if user is signed in
     */
    SET_LOCALE(new SetLocaleCommand()),
    /**
     * Authorizes user on site
     */
    LOGIN(new LoginCommand()),
    /**
     * Logs out user from the site
     */
    LOGOUT(new LogoutCommand()),
    /**
     * Registers new user on site
     */
    REGISTER(new RegisterCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
