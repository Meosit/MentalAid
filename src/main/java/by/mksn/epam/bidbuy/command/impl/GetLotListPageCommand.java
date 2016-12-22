package by.mksn.epam.bidbuy.command.impl;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.command.resource.PathManager;
import by.mksn.epam.bidbuy.entity.Lot;
import by.mksn.epam.bidbuy.service.LotService;
import by.mksn.epam.bidbuy.service.exception.ServiceException;
import by.mksn.epam.bidbuy.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.mksn.epam.bidbuy.command.resource.Constants.LOT_LIST_ATTRIBUTE;

public class GetLotListPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        LotService lotService = ServiceFactory.getInstance().getLotService();
        try {
            List<Lot> lots = lotService.getLotsByStatus(Lot.STATUS_OPENED);
            request.setAttribute(LOT_LIST_ATTRIBUTE, lots);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        String pagePath = PathManager.getProperty(PathManager.LOT_LIST);
        try {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException e) {
            throw new CommandException("Servlet exception occurs. ", e);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e);
        }
    }
}
