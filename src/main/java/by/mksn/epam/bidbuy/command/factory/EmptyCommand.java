package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.manager.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {

    private static final Logger logger = Logger.getLogger(EmptyCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        logger.debug("Empty command has been executed.");
        return PathManager.getProperty(PathManager.INDEX);
    }
}
