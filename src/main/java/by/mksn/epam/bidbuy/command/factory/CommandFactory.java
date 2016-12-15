package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.mksn.epam.bidbuy.util.StringUtil.isNullOrEmpty;

/**
 * Provides factory method which creates {@link Command} instance
 * based on {@link #COMMAND_PARAMETER} in {@link HttpServletRequest} specified
 */
public class CommandFactory {

    private static final Logger logger = Logger.getLogger(CommandFactory.class);

    /**
     * Name of request parameter which must contain command name
     */
    private static final String COMMAND_PARAMETER = "cmd";

    /**
     * Defines Concrete {@link Command} implementation based on
     * {@link #COMMAND_PARAMETER} in {@link HttpServletRequest} specified.
     * {@link CommandEnum} used for mapping
     *
     * @param request request for concrete command implementation
     * @return Concrete command implementation
     */
    public static Command defineCommand(HttpServletRequest request) {
        String actionName = request.getParameter(COMMAND_PARAMETER);
        Command resultCommand = new UnresolvedCommand();
        try {
            if (!isNullOrEmpty(actionName)) {
                resultCommand = CommandEnum.valueOf(actionName.toUpperCase()).getCommand();
            } else {
                if (actionName == null) {
                    resultCommand = CommandEnum.GET_HOME_PAGE.getCommand();
                }
                logger.warn("Command name is empty (value: \"" + actionName + "\")");
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Command with specified name not found. (name: " + actionName + ")");
        }
        return resultCommand;
    }

}
