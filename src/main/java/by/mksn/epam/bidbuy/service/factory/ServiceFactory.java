package by.mksn.epam.bidbuy.service.factory;

import by.mksn.epam.bidbuy.service.LotService;
import by.mksn.epam.bidbuy.service.UserService;
import by.mksn.epam.bidbuy.service.impl.LotServiceImpl;
import by.mksn.epam.bidbuy.service.impl.UserServiceImpl;

/**
 * Provides access to service layer objects
 */
public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();
    private UserService userService;
    private LotService lotService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
        lotService = new LotServiceImpl();
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public LotService getLotService() {
        return lotService;
    }
}
