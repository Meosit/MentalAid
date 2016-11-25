package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;

public enum CommandEnum {

    ;

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
