package by.mksn.epam.bidbuy.command.impl;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.command.resource.PathManager;
import by.mksn.epam.bidbuy.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.mksn.epam.bidbuy.command.resource.Constants.USER_ATTRIBUTE;

/**
 * Changes site content locale and saves it in database if user is signed in
 */
public class SetLocaleCommand implements Command {

    private static final Logger logger = Logger.getLogger(SetLocaleCommand.class);

    private static final String LOCALE_PARAMETER = "locale";
    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String newLocale = request.getParameter(LOCALE_PARAMETER);
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, newLocale);
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        if (user != null) {
            //TODO: save locale to database
        }
        logger.debug("Locale was set to \"" + newLocale + "\"");
        String pagePath = PathManager.getProperty(PathManager.HOME);
        try {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException e) {
            throw new CommandException("Servlet exception occurs. ", e);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e);
        }
    }
}
