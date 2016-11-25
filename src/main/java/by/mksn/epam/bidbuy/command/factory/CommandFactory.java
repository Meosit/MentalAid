package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    private static final Logger logger = Logger.getLogger(CommandFactory.class);

    private static final String COMMAND_PARAM = "command";

    public static Command defineCommand(HttpServletRequest request) {
        String actionName = request.getParameter(COMMAND_PARAM);
        if (isNullOrEmpty(actionName)) {
            logger.debug("Command name null or empty (value: " + actionName + ")");
            return null;
        }
        try {
            return CommandEnum.valueOf(actionName.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            logger.debug("Command not found (name: " + actionName + ")");
            return null;
        }
    }

    public static boolean isNullOrEmpty(String string) {
        return (string == null) || (string.isEmpty());
    }
}
