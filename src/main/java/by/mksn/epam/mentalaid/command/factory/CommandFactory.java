package by.mksn.epam.mentalaid.command.factory;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.resource.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.mksn.epam.mentalaid.command.resource.Constants.COMMAND_PARAMETER;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
import static by.mksn.epam.mentalaid.util.StringUtil.isNullOrEmpty;

public class CommandFactory {

    private static final Logger logger = Logger.getLogger(CommandFactory.class);

    /**
     * Defines Concrete {@link Command} implementation based on
     * {@link Constants#COMMAND_PARAMETER} in {@link HttpServletRequest} specified.
     * {@link CommandEnum} used for mapping
     *
     * @param request request for concrete command implementation
     * @return Concrete command implementation
     */
    public static Command defineCommand(HttpServletRequest request) {
        String actionName = request.getParameter(COMMAND_PARAMETER);
        Command resultCommand = new UnresolvedCommand();
        if (!isNullOrEmpty(actionName)) {
            try {
                resultCommand = CommandEnum.valueOf(actionName.toUpperCase()).getCommand();
            } catch (IllegalArgumentException e) {
                logger.warn("Command with specified name not found. (name: " + actionName + ")");
            }
        } else {
            if (isNull(actionName)) {
                resultCommand = CommandEnum.HOME.getCommand();
            } else {
                logger.warn("Command name is empty (value: \"" + actionName + "\")");
            }
        }

        return resultCommand;
    }

}
