package by.mksn.epam.mentalaid.util.caller;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.service.exception.ServiceDaoException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

/**
 * Provides wrapper methods for DAO call from the service.
 * Useful for remove code duplicating.
 */
public class DAOCaller {

    /**
     * Calls specified delegate in {@link DAOException} handling scope
     *
     * @param function delegate to call
     * @param <R>         return type of DAO call
     * @return result of dao call
     * @throws ServiceDaoException if DAO exception occurs during execution {@code function}
     */
    public static <R> R tryCallDAO(ServiceFunction<R> function) throws ServiceException {
        try {
            return function.apply();
        } catch (DAOException e) {
            throw new ServiceDaoException(e);
        }
    }

    /**
     * Calls specified delegate in {@link DAOException} handling scope
     *
     * @param daoFunction delegate to call
     * @throws ServiceDaoException if DAO exception occurs during execution {@code daoFunction}
     */
    public static void tryCallDAO(ServiceVoidFunction daoFunction) throws ServiceException {
        try {
            daoFunction.apply();
        } catch (DAOException e) {
            throw new ServiceDaoException(e);
        }
    }

    /**
     * Service delegate which accepts no parameter and returns object.
     * If error happens during execution, {@link DAOException} will be thrown.<br>
     * May be thrown {@link ServiceException} as well.
     *
     * @param <R> return type of delegate
     */
    @FunctionalInterface
    public interface ServiceFunction<R> {
        R apply() throws DAOException, ServiceException;
    }

    /**
     * Service delegate which accepts no parameter and returns nothing.
     * If error happens during execution, {@link DAOException} will be thrown.<br>
     * May be thrown {@link ServiceException} as well.
     */
    @FunctionalInterface
    public interface ServiceVoidFunction {
        void apply() throws DAOException, ServiceException;
    }

}
