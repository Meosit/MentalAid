package by.mksn.epam.bidbuy.service.factory;

import by.mksn.epam.bidbuy.service.UserService;
import by.mksn.epam.bidbuy.service.impl.UserServiceImpl;

/**
 * Provides access to service layer objects
 */
public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();
    private UserService userService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

}
