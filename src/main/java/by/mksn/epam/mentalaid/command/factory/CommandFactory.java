package by.mksn.epam.mentalaid.command.factory;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.resource.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.mksn.epam.mentalaid.command.resource.Constants.COMMAND_PARAMETER;
import static by.mksn.epam.mentalaid.command.resource.Constants.FROM_URL_PARAMETER;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
import static by.mksn.epam.mentalaid.util.NullUtil.isNullOrEmpty;

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
                resultCommand = CommandEnum.INDEX.getCommand();
            } else {
                logger.warn("Command name is empty (value: \"" + actionName + "\")");
            }
        }

        return resultCommand;
    }

    /**
     * Defines 'from' url for back-redirecting mechanism.
     * Request must contains parameter {@link Constants#FROM_URL_PARAMETER} with string in
     * format "{@code command_name param_value1 param_value2}" where<br>
     * - {@code command_name} is name of the command to redirecting <br>
     * - param values which needed to form this redirect url.<br>
     * All string elements separated with space (in HTML there is plus sign)<br><br>
     * <p>
     * If invalid string format or undefined command name passed,
     * application entry point URL will be returned.
     *
     * @param request request from where from parameter will be taken
     * @return string which represents formed URL to redirect, based on {@link Constants#FROM_URL_PARAMETER}
     * @see Command#generateFromUrl(String, String[])
     */
    public static String defineFromUrl(HttpServletRequest request) {
        String fromParameter = request.getParameter(FROM_URL_PARAMETER);
        String resultUrl = request.getContextPath();
        if (!isNullOrEmpty(fromParameter)) {
            String[] fromParameterArgs = fromParameter.split(" ");
            if (fromParameterArgs.length > 0) {
                try {
                    CommandEnum targetCommandEnumItem = CommandEnum.valueOf(fromParameterArgs[0].toUpperCase());
                    resultUrl = resultUrl + request.getServletPath()
                            + '?' + COMMAND_PARAMETER + '=' + targetCommandEnumItem.toString().toLowerCase();
                    resultUrl = targetCommandEnumItem.getCommand().generateFromUrl(resultUrl, fromParameterArgs);
                } catch (IllegalArgumentException e) {
                    logger.warn("Invalid '" + FROM_URL_PARAMETER + "' parameter passed ('" + fromParameter + "')");
                }
            } else {
                logger.warn("Invalid '" + FROM_URL_PARAMETER + "' parameter passed ('" + fromParameter + "')");
            }
        }
        logger.debug("Defined from url = '" + resultUrl + "'");
        return resultUrl;
    }

}
