package by.mksn.epam.mentalaid.dao.impl;

import by.mksn.epam.mentalaid.dao.UserDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.pool.ConnectionPool;
import by.mksn.epam.mentalaid.dao.pool.exception.PoolException;
import by.mksn.epam.mentalaid.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySQL implementation of {@link UserDAO}
 */
public class MySqlUserDAO extends AbstractBaseDAO<User> implements UserDAO {

    private static final String QUERY_SELECT_BY_ID = "SELECT `user`.`id`, `user`.`email`, `user`.`username`, `user`.`pass_hash`, `user`.`role`, `user`.`created_at`, `user`.`modified_at`, `user`.`status`, `user`.`locale`, `user`.`image_url`, `user`.`website`, (SELECT AVG(`mark`.`value`) FROM `mark` WHERE `mark`.`answer_id` IN (SELECT `answer`.`id` FROM `answer` WHERE `answer`.`creator_id` = `user`.`id`)) AS averageMark FROM `user` " +
            "WHERE `id` = ?;";
    private static final String QUERY_SELECT_BY_USERNAME = "SELECT `user`.`id`, `user`.`email`, `user`.`username`, `user`.`pass_hash`, `user`.`role`, `user`.`created_at`, `user`.`modified_at`, `user`.`status`, `user`.`locale`, `user`.`image_url`, `user`.`website`, (SELECT AVG(`mark`.`value`) FROM `mark` WHERE `mark`.`answer_id` IN (SELECT `answer`.`id` FROM `answer` WHERE `answer`.`creator_id` = `user`.`id`)) AS averageMark FROM `user` " +
            "WHERE (`username` = ?);";
    private static final String QUERY_SELECT_BY_EMAIL = "SELECT `user`.`id`, `user`.`email`, `user`.`username`, `user`.`pass_hash`, `user`.`role`, `user`.`created_at`, `user`.`modified_at`, `user`.`status`, `user`.`locale`, `user`.`image_url`, `user`.`website`, (SELECT AVG(`mark`.`value`) FROM `mark` WHERE `mark`.`answer_id` IN (SELECT `answer`.`id` FROM `answer` WHERE `answer`.`creator_id` = `user`.`id`)) AS averageMark FROM `user` " +
            "WHERE (`email` = ?);";
    private static final String QUERY_UPDATE = "UPDATE `user` SET `email` = ?, `username` = ?, `pass_hash` = ?, `status` = ?, `locale` = ?, `image_url` = ?, `website` = ? WHERE (`id` = ?) AND (`status` != -1);";
    private static final String QUERY_INSERT = "INSERT INTO `user` (`email`, `username`, `pass_hash`) VALUES (?, ?, ?)";
    private static final String QUERY_DELETE = "UPDATE `user` SET `status` = -1 WHERE (`id` = ?) AND (`status` != -1);";

    @Override
    public User insert(User entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPassHash());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long insertedId = keys.getLong(1);
                    entity = selectById(connection, QUERY_SELECT_BY_ID, insertedId);
                } else {
                    throw new DAOException("Generated keys set is empty");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return entity;
    }

    @Override
    public User selectById(long id) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            user = selectById(connection, QUERY_SELECT_BY_ID, id);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

    @Override
    public User selectByUsername(String username) throws DAOException {
        return selectWithStringParameter(QUERY_SELECT_BY_USERNAME, username);
    }

    @Override
    public User selectByEmail(String email) throws DAOException {
        return selectWithStringParameter(QUERY_SELECT_BY_EMAIL, email);
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
            statement.setString(6, updatedEntity.getImageUrl());
            statement.setString(7, updatedEntity.getWebsite());

            statement.setLong(8, updatedEntity.getId());
            statement.executeUpdate();

            User reselectedEntity = selectById(connection, QUERY_SELECT_BY_ID, updatedEntity.getId());
            updatedEntity.setModifiedAt(reselectedEntity.getModifiedAt());
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        delete(QUERY_DELETE, id);
    }

    private User selectWithStringParameter(String selectQuery, String parameter) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, parameter);
            user = executeStatementAndParseResultSet(statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return user;
    }

    @Override
    protected User parseResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setEmail(resultSet.getString(2));
        user.setUsername(resultSet.getString(3));
        user.setPassHash(resultSet.getString(4));
        user.setRole(resultSet.getInt(5));
        user.setCreatedAt(resultSet.getTimestamp(6));
        user.setModifiedAt(resultSet.getTimestamp(7));
        user.setStatus(resultSet.getInt(8));
        user.setLocale(resultSet.getString(9));
        user.setImageUrl(resultSet.getString(10));
        user.setWebsite(resultSet.getString(11));
        user.setAverageMark(resultSet.getFloat(12));
        return user;
    }

}
