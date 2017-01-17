package by.mksn.epam.mentalaid.dao.impl;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.pool.ConnectionPool;
import by.mksn.epam.mentalaid.dao.pool.exception.PoolException;
import by.mksn.epam.mentalaid.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.mksn.epam.mentalaid.util.StringUtil.isNullOrEmpty;

/**
 * class with some useful methods for other DAO's to avoid code duplication
 *
 * @param <E> Entity of concrete DAO
 */
abstract class AbstractBaseDAO<E extends Entity> {

    /**
     * Selects entity with specified id
     *
     * @param connection  connection to database
     * @param selectQuery select SQL query for concrete database
     * @param id          id of an entity to select
     * @return {@link Entity} with specified ID or {@code null} if entity with such ID not found
     * @throws SQLException if something went wrong
     */
    E executeSelectById(Connection connection, String selectQuery, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            return executeStatementAndParseResultSet(statement);
        }
    }

    /**
     * Deletes entity from database
     *
     * @param deleteQuery delete SQL query for concrete database
     * @param id          id of an entity
     * @throws DAOException if something went wrong
     */
    void executeDelete(String deleteQuery, long id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    /**
     * Creates concrete entity from the {@link ResultSet} without moving the cursor
     *
     * @param resultSet result set to parse
     * @return {@link Entity} with filled properties
     * @throws SQLException if something went wrong
     */
    abstract E parseResultSet(ResultSet resultSet) throws SQLException;

    /**
     * Executes specified {@link PreparedStatement}
     * and parses it result to the appropriate {@link Entity}.
     *
     * @param statement {@link PreparedStatement} to execute
     * @return {@link Entity} with filled properties
     * @throws SQLException if something went wrong
     * @see #executeStatementAndParseResultSetToList(PreparedStatement)
     */
    E executeStatementAndParseResultSet(PreparedStatement statement) throws SQLException {
        E entity;
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                entity = parseResultSet(resultSet);
            } else {
                entity = null;
            }
        }
        return entity;
    }

    /**
     * Executes specified {@link PreparedStatement}
     * and parses it result to the appropriate list of {@link Entity}.
     *
     * @param statement {@link PreparedStatement} to execute
     * @return List of {@link Entity} with filled properties
     * @throws SQLException if something went wrong
     * @see #executeStatementAndParseResultSet(PreparedStatement)
     */
    List<E> executeStatementAndParseResultSetToList(PreparedStatement statement) throws SQLException {
        ArrayList<E> questions = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                questions.add(parseResultSet(resultSet));
            }
        }
        questions.trimToSize();
        return questions;
    }

    /**
     * Creates patten for LIKE query from the specified string.
     * Also escapes LIKE special chars.
     *
     * @param query query to create pattern
     * @return LIKE pattern
     */
    String createGlobalLikePattern(String query) {
        if (!isNullOrEmpty(query)) {
            query = query
                    .replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            return "%" + query + "%";
        } else {
            return "%";
        }
    }


}
