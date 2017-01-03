package by.mksn.epam.mentalaid.dao.factory.impl;

import by.mksn.epam.mentalaid.dao.QuestionDAO;
import by.mksn.epam.mentalaid.dao.UserDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.dao.impl.MySqlQuestionDAO;
import by.mksn.epam.mentalaid.dao.impl.MySqlUserDAO;

/**
 * Concrete DAO factory for MySQL database
 */
public class MySqlDAOFactory extends DAOFactory {

    private static final MySqlDAOFactory instance = new MySqlDAOFactory();
    private UserDAO userDAO;
    private QuestionDAO questionDAO;

    private MySqlDAOFactory() {
        userDAO = new MySqlUserDAO();
        questionDAO = new MySqlQuestionDAO();
    }

    public static MySqlDAOFactory getInstance() {
        return instance;
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return questionDAO;
    }
}
