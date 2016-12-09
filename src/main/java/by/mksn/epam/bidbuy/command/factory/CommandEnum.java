package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.display.ShowLoginPageCommand;
import by.mksn.epam.bidbuy.command.display.ShowRegisterPageCommand;

/**
 * Represents full list of commands, used in factory
 */
public enum CommandEnum {
    /**
     * This command only shows page with registration form
     */
    SHOW_REGISTER(new ShowRegisterPageCommand()),
    /**
     * This command only shows page with login form
     */
    SHOW_LOGIN(new ShowLoginPageCommand())
    ;

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
