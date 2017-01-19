package by.mksn.epam.mentalaid.dao.impl;

import by.mksn.epam.mentalaid.dao.MarkDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Mark;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.mksn.epam.mentalaid.util.caller.JDBCCaller.tryCallJDBC;

public class MySqlMarkDAO extends AbstractBaseDAO<Mark> implements MarkDAO {

    private static final String QUERY_INSERT = "INSERT INTO `mark` (`user_id`, `answer_id`, `value`) VALUE (?, ?, ?);";
    private static final String QUERY_SELECT_BY_ID = "SELECT `id`, `user_id`, `answer_id`, `value`, `created_at`, `modified_at` FROM `mark` WHERE `id` = ?;";
    private static final String QUERY_SELECT_BY_USER_AND_ANSWER_ID = "SELECT `id`, `user_id`, `answer_id`, `value`, `created_at`, `modified_at` FROM `mark` WHERE (`user_id` = ?) AND (`answer_id` = ?) LIMIT 1;";
    private static final String QUERY_UPDATE = "UPDATE `mark` SET `value` = ? WHERE `id` = ?;";
    private static final String QUERY_DELETE = "DELETE FROM `mark` WHERE `id` = ?;";


    @Override
    public Mark insert(Mark entity) throws DAOException {
        return tryCallJDBC(QUERY_INSERT, (connection, statement) -> {
            statement.setLong(1, entity.getUserId());
            statement.setLong(2, entity.getAnswerId());
            statement.setInt(3, entity.getValue());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long insertedId = keys.getLong(1);
                    return executeSelectById(connection, QUERY_SELECT_BY_ID, insertedId);
                } else {
                    throw new DAOException("Generated keys set is empty");
                }
            }
        });
    }

    @Override
    public Mark selectById(long id) throws DAOException {
        return executeSelectById(QUERY_SELECT_BY_ID, id);
    }

    @Override
    public Mark selectByUserAndAnswerId(long userId, long answerId) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_BY_USER_AND_ANSWER_ID, statement -> {
            statement.setLong(1, userId);
            statement.setLong(2, answerId);
            return executeStatementAndParseResultSet(statement);
        });
    }

    @Override
    public void update(Mark updatedEntity) throws DAOException {
        tryCallJDBC(QUERY_UPDATE, ((connection, statement) -> {
            statement.setInt(1, updatedEntity.getValue());
            statement.setLong(2, updatedEntity.getId());
            statement.executeUpdate();
            Mark reselectedEntity = executeSelectById(connection, QUERY_SELECT_BY_ID, updatedEntity.getId());
            updatedEntity.setModifiedAt(reselectedEntity.getModifiedAt());
        }));
    }

    @Override
    public void delete(long id) throws DAOException {
        executeDelete(QUERY_DELETE, id);
    }

    @Override
    Mark parseResultSet(ResultSet resultSet) throws SQLException {
        Mark mark = new Mark();
        mark.setId(resultSet.getLong(1));
        mark.setUserId(resultSet.getLong(2));
        mark.setAnswerId(resultSet.getLong(3));
        mark.setValue(resultSet.getInt(4));
        mark.setCreatedAt(resultSet.getTimestamp(5));
        mark.setModifiedAt(resultSet.getTimestamp(6));
        return mark;
    }
}
