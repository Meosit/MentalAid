package by.mksn.epam.mentalaid.util.caller;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.pool.ConnectionPool;
import by.mksn.epam.mentalaid.dao.pool.exception.PoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Provides wrapper methods for JDBC call from the DAO.
 * Useful for remove code duplicating.
 */
public class JDBCCaller {

    /**
     * Method gets connection from the {@link ConnectionPool}, creates {@link PreparedStatement}
     * from the specified query and calls specified delegate in {@link SQLException} and {@link PoolException} handling scope.
     * Method ensures that connection and statement will be closed after executing delegate.
     *
     * @param query    sql query from which prepared statement will be created.
     * @param function delegate to call
     * @param <R>      return type of delegate
     * @return result of the delegate call
     * @throws DAOException if {@link SQLException} or {@link PoolException} happens during execution
     */
    public static <R> R tryCallJDBC(String query, DAOFunction<R> function) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            return function.apply(connection, statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    /**
     * Method gets connection from the {@link ConnectionPool}, creates {@link PreparedStatement}
     * from the specified query and calls specified delegate in {@link SQLException} and {@link PoolException} handling scope.
     * Method ensures that connection and statement will be closed after executing delegate.
     *
     * @param query    sql query from which prepared statement will be created.
     * @param function delegate to call
     * @throws DAOException if {@link SQLException} or {@link PoolException} happens during execution
     */
    public static void tryCallJDBC(String query, DAOVoidFunction function) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            function.apply(connection, statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    /**
     * Method gets connection from the {@link ConnectionPool}, creates {@link PreparedStatement}
     * from the specified query and calls specified delegate in {@link SQLException} and {@link PoolException} handling scope.
     * Method ensures that connection and statement will be closed after executing delegate.
     *
     * @param query    sql query from which prepared statement will be created.
     * @param function delegate to call
     * @param <R>      return type of delegate
     * @return result of the delegate call
     * @throws DAOException if {@link SQLException} or {@link PoolException} happens during execution
     */
    public static <R> R tryCallJDBC(String query, DAOFunctionWithoutConnection<R> function) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            return function.apply(statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    /**
     * Method gets connection from the {@link ConnectionPool}, creates {@link PreparedStatement}
     * from the specified query and calls specified delegate in {@link SQLException} and {@link PoolException} handling scope.
     * Method ensures that connection and statement will be closed after executing delegate.
     *
     * @param query    sql query from which prepared statement will be created.
     * @param function delegate to call
     * @throws DAOException if {@link SQLException} or {@link PoolException} happens during execution
     */
    public static void tryCallJDBC(String query, DAOVoidFunctionWithoutConnection function) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            function.apply(statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    /**
     * DAO delegate which accepts JDBC connection, created prepared statement from this connection and
     * returns an object.
     *
     * @param <R> return type of delegate
     */
    @FunctionalInterface
    public interface DAOFunction<R> {
        R apply(Connection connection, PreparedStatement statement) throws SQLException, PoolException, DAOException;
    }

    /**
     * DAO delegate which accepts JDBC connection, created prepared statement from this connection and
     * returns nothing.
     */
    @FunctionalInterface
    public interface DAOVoidFunction {
        void apply(Connection connection, PreparedStatement statement) throws SQLException, PoolException, DAOException;
    }

    /**
     * DAO delegate which accepts created prepared statement and
     * returns an object
     *
     * @param <R> return type of delegate
     */
    @FunctionalInterface
    public interface DAOFunctionWithoutConnection<R> {
        R apply(PreparedStatement statement) throws SQLException, PoolException, DAOException;
    }

    /**
     * DAO delegate which accepts created prepared statement and
     * returns nothing
     */
    @FunctionalInterface
    public interface DAOVoidFunctionWithoutConnection {
        void apply(PreparedStatement statement) throws SQLException, PoolException, DAOException;
    }

}
