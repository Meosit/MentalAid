package by.mksn.epam.mentalaid.dao.factory.impl;

import by.mksn.epam.mentalaid.dao.AnswerDAO;
import by.mksn.epam.mentalaid.dao.QuestionDAO;
import by.mksn.epam.mentalaid.dao.UserDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.dao.impl.MySqlAnswerDAO;
import by.mksn.epam.mentalaid.dao.impl.MySqlQuestionDAO;
import by.mksn.epam.mentalaid.dao.impl.MySqlUserDAO;

/**
 * Concrete DAO factory for MySQL database
 */
public class MySqlDAOFactory extends DAOFactory {

    private static final MySqlDAOFactory instance = new MySqlDAOFactory();
    private UserDAO userDAO;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;

    private MySqlDAOFactory() {
        userDAO = new MySqlUserDAO();
        questionDAO = new MySqlQuestionDAO();
        answerDAO = new MySqlAnswerDAO();
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

    @Override
    public AnswerDAO getAnswerDAO() {
        return answerDAO;
    }
}
