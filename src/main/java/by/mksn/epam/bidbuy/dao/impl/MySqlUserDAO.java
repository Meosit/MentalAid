package by.mksn.epam.bidbuy.dao.impl;

import by.mksn.epam.bidbuy.dao.UserDAO;
import by.mksn.epam.bidbuy.dao.exception.DAOException;
import by.mksn.epam.bidbuy.dao.impl.pool.ConnectionPool;
import by.mksn.epam.bidbuy.dao.impl.pool.exception.PoolException;
import by.mksn.epam.bidbuy.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;

public class MySqlUserDAO implements UserDAO {

    private static final Logger logger = Logger.getLogger(MySqlUserDAO.class);
    private static final String QUERY_SELECT_BY_ID = "SELECT `id`, `email`, `username`, `pass_hash`, `role`, `created_at`, `modified_at`, `status`, `locale` FROM `user` WHERE (`id` = ?) AND (`status` <> -1);";
    private static final String QUERY_UPDATE = "UPDATE `user` SET `email` = ?, `username` = ?, `pass_hash` = ?, `status` = ?, `locale` = ? WHERE `id` = ?";
    private static final String QUERY_INSERT = "INSERT INTO `user` (`email`, `username`, `pass_hash`, `locale`) VALUES (?, ?, ?, ?)";
    private static final String QUERY_DELETE = "UPDATE `user` SET `status` = -1 WHERE `id` = ?";

    @Override
    public User selectById(long id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement selectAllStatement = connection.createStatement();
             ResultSet resultSet = selectAllStatement.executeQuery(QUERY_SELECT_BY_ID)) {
            User user;
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
            return user;
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public void update(User updatedUser) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
            statement.setString(1, updatedUser.getEmail());
            statement.setString(2, updatedUser.getUsername());
            statement.setString(3, updatedUser.getPassHash());
            statement.setInt(4, updatedUser.getStatus());
            statement.setInt(4, updatedUser.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
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
    public void insert(User entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPassHash());
            statement.setString(4, entity.getLocale());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot execute statement or close connection\n", e);
            throw new DAOException("Cannot execute statement or close connection", e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

}
