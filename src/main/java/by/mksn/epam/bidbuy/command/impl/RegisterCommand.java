package by.mksn.epam.bidbuy.command.impl;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command to register a new user. This is async command.
 */
public class RegisterCommand implements Command {

    private static final Logger logger = Logger.getLogger(RegisterCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            logger.debug("Form: " + new JSONObject(request.getParameterMap()).toString(2));
        } catch (JSONException e) {
            logger.error(e);
        }
    }
}
