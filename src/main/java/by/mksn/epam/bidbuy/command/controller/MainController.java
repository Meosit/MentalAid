package by.mksn.epam.bidbuy.command.controller;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.command.factory.CommandFactory;
import by.mksn.epam.bidbuy.pool.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main Controller of web app
 */
@WebServlet("/controller")
public class MainController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(MainController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().releasePool();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pagePath;
        Command command = CommandFactory.defineCommand(request);
        try {
            pagePath = command.execute(request);
        } catch (CommandException e) {
            logger.error("Cannot execute command.\n", e);
            throw new ServletException("Cannot execute command.", e);
        }
        if (pagePath != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pagePath);
            logger.trace("Forward to " + pagePath);
            dispatcher.forward(request, response);
        }
    }
}
