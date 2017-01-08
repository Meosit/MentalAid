package by.mksn.epam.mentalaid.dao.factory;

import by.mksn.epam.mentalaid.dao.AnswerDAO;
import by.mksn.epam.mentalaid.dao.QuestionDAO;
import by.mksn.epam.mentalaid.dao.UserDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOFactoryNotFoundException;
import by.mksn.epam.mentalaid.dao.factory.impl.MySqlDAOFactory;
import by.mksn.epam.mentalaid.dao.manager.DatabaseManager;
import org.apache.log4j.Logger;

/**
 * Provides basic access for DAO
 */
public abstract class DAOFactory {

    /**
     * DAO type for MySQL / MariaDB databases
     */
    private static final String MY_SQL = "MySQL";

    private static final String DATABASE_TYPE = DatabaseManager.getProperty(DatabaseManager.DATABASE_TYPE);
    private static final Logger logger = Logger.getLogger(DAOFactory.class);

    /**
     * Returns instance of concrete factory with for the specified type of database
     *
     * @return concrete factory instance
     */
    public static DAOFactory getDAOFactory() {
        DAOFactory result;
        switch (DATABASE_TYPE) {
            case MY_SQL:
                result = MySqlDAOFactory.getInstance();
                break;
            default:
                logger.fatal("DAO Factory with for type " + DATABASE_TYPE + " not found.");
                throw new DAOFactoryNotFoundException();
        }
        return result;
    }

    /**
     * Returns concrete implementation of UserDAO for appropriate database
     *
     * @return concrete {@link UserDAO}
     */
    public abstract UserDAO getUserDAO();

    /**
     * Returns concrete implementation of QuestionDAO for appropriate database
     *
     * @return concrete {@link QuestionDAO}
     */
    public abstract QuestionDAO getQuestionDAO();

    /**
     * Returns concrete implementation of AnswerDAO for appropriate database
     *
     * @return concrete {@link AnswerDAO}
     */
    public abstract AnswerDAO getAnswerDAO();


}
