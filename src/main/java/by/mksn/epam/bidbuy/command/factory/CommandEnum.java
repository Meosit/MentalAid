package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.impl.GetLoginPageCommand;
import by.mksn.epam.bidbuy.command.impl.GetRegisterPageCommand;
import by.mksn.epam.bidbuy.command.impl.SetLocaleCommand;
import by.mksn.epam.bidbuy.command.impl.acync.LoginCommand;

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
     * Changes site content locale and saves it in database if user is signed in
     */
    SET_LOCALE(new SetLocaleCommand()),
    /**
     * Authorizes user on site
     */
    LOGIN(new LoginCommand())
    ;

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
