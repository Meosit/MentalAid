package by.mksn.epam.bidbuy.dao.factory;

import by.mksn.epam.bidbuy.dao.LotDAO;
import by.mksn.epam.bidbuy.dao.UserDAO;
import by.mksn.epam.bidbuy.dao.exception.DAOFactoryNotFoundException;
import by.mksn.epam.bidbuy.dao.factory.impl.MySqlDAOFactory;
import org.apache.log4j.Logger;

/**
 * Provides basic access for DAO
 */
public abstract class DAOFactory {

    public static final int MY_SQL = 1;
    private static final Logger logger = Logger.getLogger(DAOFactory.class);

    /**
     * Returns instance of concrete factory with for the specified type of database
     *
     * @param type database type. At now supported only {@link #MY_SQL}
     * @return concrete factory instance
     */
    public static DAOFactory getDAOFactory(int type) {
        DAOFactory result;
        switch (type) {
            case MY_SQL:
                result = MySqlDAOFactory.getInstance();
                break;
            default:
                logger.fatal("DAO Factory with for type " + type + " not found.");
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
     * Returns concrete implementation of LotDAO for appropriate database
     *
     * @return concrete {@link LotDAO}
     */
    public abstract LotDAO getLotDAO();


}
