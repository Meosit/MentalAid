package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.impl.content.GetLoginPageCommand;
import by.mksn.epam.bidbuy.command.impl.content.GetRegisterPageCommand;

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
    GET_LOGIN_PAGE(new GetLoginPageCommand())
    ;

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
