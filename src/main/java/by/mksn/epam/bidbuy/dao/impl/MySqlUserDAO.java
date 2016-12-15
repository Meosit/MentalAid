package by.mksn.epam.bidbuy.dao.impl;

import by.mksn.epam.bidbuy.dao.UserDAO;
import by.mksn.epam.bidbuy.dao.exception.DAOException;
import by.mksn.epam.bidbuy.dao.pool.ConnectionPool;
import by.mksn.epam.bidbuy.dao.pool.exception.PoolException;
import by.mksn.epam.bidbuy.entity.User;
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
    private static final String QUERY_SELECT_BY_ID = "SELECT `id`, `email`, `username`, `pass_hash`, `role`, `created_at`, `modified_at`, `status`, `locale` FROM `user` WHERE (`id` = ?) AND (`status` <> -1);";
    private static final String QUERY_SELECT_BY_USERNAME = "SELECT `id`, `email`, `username`, `pass_hash`, `role`, `created_at`, `modified_at`, `status`, `locale` FROM `user` WHERE (`username` = ?) AND (`status` <> -1);";
    private static final String QUERY_SELECT_BY_EMAIL = "SELECT `id`, `email`, `username`, `pass_hash`, `role`, `created_at`, `modified_at`, `status`, `locale` FROM `user` WHERE (`email` = ?) AND (`status` <> -1);";
    private static final String QUERY_UPDATE = "UPDATE `user` SET `email` = ?, `username` = ?, `pass_hash` = ?, `status` = ?, `locale` = ? WHERE `id` = ?";
    private static final String QUERY_INSERT = "INSERT INTO `user` (`email`, `username`, `pass_hash`, `locale`) VALUES (?, ?, ?, ?)";
    private static final String QUERY_DELETE = "UPDATE `user` SET `status` = -1 WHERE `id` = ?";

    @Override
    public User selectById(long id) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            user = selectById(connection, id);
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

    private User selectById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setLong(1, id);
            return parseUserResultSet(statement);
        }
    }

    @Override
    public User selectByUsername(String username) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            user = selectByUsername(connection, username);
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
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
                user = parseUserResultSet(statement);
            }
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

    private User selectByUsername(Connection connection, String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_USERNAME)) {
            statement.setString(1, username);
            return parseUserResultSet(statement);
        }
    }

    private User parseUserResultSet(PreparedStatement statement) throws SQLException {
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

    @Override
    public void update(User updatedUser) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            update(connection, updatedUser);
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    private void update(Connection connection, User updatedUser) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
            statement.setString(1, updatedUser.getEmail());
            statement.setString(2, updatedUser.getUsername());
            statement.setString(3, updatedUser.getPassHash());
            statement.setInt(4, updatedUser.getStatus());
            statement.setString(5, updatedUser.getLocale());
            statement.setLong(6, updatedUser.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public User insert(String username, String email, String passHash) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setString(3, passHash);
            statement.executeUpdate();

            user = selectByUsername(connection, username);
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

}
