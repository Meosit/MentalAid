package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

/**
 * Provides wrapper for DAO call from the service.
 * Useful for remove code duplicating.
 */
class DAOCaller {

    /**
     * Wrap passed {@link DAOFunction} with exception handling
     *
     * @param daoFunction delegate to call
     * @param <R>         return type of DAO call
     * @return result of dao call
     * @throws ServiceException if DAO exception occurs during execution {@code daoFunction}
     */
    static <R> R tryCallDAO(DAOFunction<R> daoFunction) throws ServiceException {
        try {
            return daoFunction.apply();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Wrap passed {@link DAOFunction} with exception handling
     *
     * @param daoFunction delegate to call
     * @throws ServiceException if DAO exception occurs during execution {@code daoFunction}
     */
    static void tryCallDAO(VoidDAOFunction daoFunction) throws ServiceException {
        try {
            daoFunction.apply();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Dao delegate which accepts no parameter and returns object.
     * If error happens during execution, {@link DAOException} will be thrown.<br>
     * May be thrown {@link ServiceException} as well.
     *
     * @param <R> return type of
     */
    @FunctionalInterface
    interface DAOFunction<R> {
        R apply() throws DAOException, ServiceException;
    }

    /**
     * Dao delegate which accepts no parameter and not return anything.
     * If error happens during execution, {@link DAOException} will be thrown.<br>
     * May be thrown {@link ServiceException} as well.
     */
    @FunctionalInterface
    interface VoidDAOFunction {
        void apply() throws DAOException, ServiceException;
    }

}
