package by.mksn.epam.mentalaid.dao.impl;

import by.mksn.epam.mentalaid.dao.UserDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.pool.ConnectionPool;
import by.mksn.epam.mentalaid.dao.pool.exception.PoolException;
import by.mksn.epam.mentalaid.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySQL implementation of {@link UserDAO}
 */
public class MySqlUserDAO implements UserDAO {

    private static final Logger logger = Logger.getLogger(MySqlUserDAO.class);
    private static final String QUERY_SELECT_BY_ID = "SELECT `id`, `email`, `username`, `pass_hash`, `role`, `created_at`, `modified_at`, `status`, `locale` FROM `user` WHERE (`id` = ?);";
    private static final String QUERY_SELECT_BY_USERNAME = "SELECT `id`, `email`, `username`, `pass_hash`, `role`, `created_at`, `modified_at`, `status`, `locale` FROM `user` WHERE (`username` = ?);";
    private static final String QUERY_SELECT_BY_EMAIL = "SELECT `id`, `email`, `username`, `pass_hash`, `role`, `created_at`, `modified_at`, `status`, `locale` FROM `user` WHERE (`email` = ?);";
    private static final String QUERY_UPDATE = "UPDATE `user` SET `email` = ?, `username` = ?, `pass_hash` = ?, `status` = ?, `locale` = ? WHERE `id` = ?";
    private static final String QUERY_INSERT = "INSERT INTO `user` (`email`, `username`, `pass_hash`) VALUES (?, ?, ?)";
    private static final String QUERY_DELETE = "UPDATE `user` SET `status` = -1 WHERE `id` = ?";

    @Override
    public User insert(User user) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassHash());
            statement.executeUpdate();

            user = selectByUsername(connection, user.getUsername());
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

    @Override
    public User selectById(long id) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            user = selectById(connection, id);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

    @Override
    public User selectByUsername(String username) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            user = selectByUsername(connection, username);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

    @Override
    public User selectByEmail(String email) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_EMAIL)) {
                statement.setString(1, email);
                user = executeStatementAndParseResultSet(statement);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

    @Override
    public void update(User updatedEntity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
            statement.setString(1, updatedEntity.getEmail());
            statement.setString(2, updatedEntity.getUsername());
            statement.setString(3, updatedEntity.getPassHash());
            statement.setInt(4, updatedEntity.getStatus());
            statement.setString(5, updatedEntity.getLocale());

            statement.setLong(6, updatedEntity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    private User selectById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setLong(1, id);
            return executeStatementAndParseResultSet(statement);
        }
    }

    private User selectByUsername(Connection connection, String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_USERNAME)) {
            statement.setString(1, username);
            return executeStatementAndParseResultSet(statement);
        }
    }

    private User executeStatementAndParseResultSet(PreparedStatement statement) throws SQLException {
        User user;
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(1));
                user.setEmail(resultSet.getString(2));
                user.setUsername(resultSet.getString(3));
                user.setPassHash(resultSet.getString(4));
                user.setRole(resultSet.getInt(5));
                user.setCreatedAt(resultSet.getTimestamp(6));
                user.setModifiedAt(resultSet.getTimestamp(7));
                user.setStatus(resultSet.getInt(8));
                user.setLocale(resultSet.getString(9));
            } else {
                user = null;
            }
        }
        return user;
    }

}
