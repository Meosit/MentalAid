package by.mksn.epam.bidbuy.command.exception;

public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(String cause) {
        super(cause);
    }

    public CommandException(Throwable t) {
        super(t);
    }

    public CommandException(String cause, Throwable t) {
        super(cause, t);
    }

}
