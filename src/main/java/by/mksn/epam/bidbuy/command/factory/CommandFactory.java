package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    private static final Logger logger = Logger.getLogger(CommandFactory.class);

    private static final String COMMAND_PARAMETER = "cmd";

    public static Command defineCommand(HttpServletRequest request) {
        String actionName = request.getParameter(COMMAND_PARAMETER);
        Command resultCommand = new EmptyCommand();
        try {
            if (!isNullOrEmpty(actionName)) {
                resultCommand = CommandEnum.valueOf(actionName.toUpperCase()).getCommand();
            } else {
                logger.warn("Command name null or empty (value: " + actionName + ")");
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Command with specified name not found. (name: " + actionName + ")");
        }
        return resultCommand;
    }

    public static boolean isNullOrEmpty(String string) {
        return (string == null) || (string.isEmpty());
    }
}
