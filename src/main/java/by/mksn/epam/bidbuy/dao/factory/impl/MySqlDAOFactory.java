package by.mksn.epam.bidbuy.dao.factory.impl;

import by.mksn.epam.bidbuy.dao.LotDAO;
import by.mksn.epam.bidbuy.dao.UserDAO;
import by.mksn.epam.bidbuy.dao.factory.DAOFactory;
import by.mksn.epam.bidbuy.dao.impl.MySqlLotDAO;
import by.mksn.epam.bidbuy.dao.impl.MySqlUserDAO;

/**
 * Concrete DAO factory for MySQL database
 */
public class MySqlDAOFactory extends DAOFactory {

    private static final MySqlDAOFactory instance = new MySqlDAOFactory();
    private UserDAO userDAO;
    private LotDAO lotDAO;

    private MySqlDAOFactory() {
        userDAO = new MySqlUserDAO();
        lotDAO = new MySqlLotDAO();
    }

    public static MySqlDAOFactory getInstance() {
        return instance;
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public LotDAO getLotDAO() {
        return lotDAO;
    }
}
