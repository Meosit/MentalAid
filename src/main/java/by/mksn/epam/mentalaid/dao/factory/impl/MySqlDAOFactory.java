package by.mksn.epam.mentalaid.dao.factory.impl;

import by.mksn.epam.mentalaid.dao.UserDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.dao.impl.MySqlUserDAO;

/**
 * Concrete DAO factory for MySQL database
 */
public class MySqlDAOFactory extends DAOFactory {

    private static final MySqlDAOFactory instance = new MySqlDAOFactory();
    private UserDAO userDAO;

    private MySqlDAOFactory() {
        userDAO = new MySqlUserDAO();
    }

    public static MySqlDAOFactory getInstance() {
        return instance;
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }
}
