package by.mksn.epam.mentalaid.dao.impl;

import by.mksn.epam.mentalaid.dao.AnswerDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.pool.ConnectionPool;
import by.mksn.epam.mentalaid.dao.pool.exception.PoolException;
import by.mksn.epam.mentalaid.entity.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySqlAnswerDAO extends AbstractBaseDAO<Answer> implements AnswerDAO {

    private static final String QUERY_INSERT = "INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (?, ?, ?);";
    private static final String QUERY_SELECT_BY_ID = "SELECT `answer`.`id`, `answer`.`question_id`, `answer`.`creator_id`, `answer`.`text`, `answer`.`status`, `answer`.`created_at`, `answer`.`modified_at`, AVG(`mark`.`value`), COUNT(`mark`.`value`), `user`.`username` FROM `answer` JOIN `user` ON `answer`.`creator_id` = `user`.`id` LEFT JOIN `mark` ON `answer`.`id` = `mark`.`answer_id` WHERE (`answer`.`id` = ?) AND (`answer`.`status` != -1) GROUP BY `answer`.`id`;";
    private static final String QUERY_SELECT_BY_QUESTION_ID = "SELECT `answer`.`id`, `answer`.`question_id`, `answer`.`creator_id`, `answer`.`text`, `answer`.`status`, `answer`.`created_at`, `answer`.`modified_at`, AVG(`mark`.`value`) AS 'averageMark', COUNT(`mark`.`value`), `user`.`username` FROM `answer` JOIN `user` ON `answer`.`creator_id` = `user`.`id` LEFT JOIN `mark` ON `answer`.`id` = `mark`.`answer_id` WHERE (`answer`.`question_id` = ?) AND (`answer`.`status` != -1) GROUP BY `answer`.`id` ORDER BY averageMark DESC, `answer`.`id`;";
    private static final String QUERY_UPDATE = "UPDATE `answer` SET `text` = ? WHERE (`id` = ?) AND (`status` != -1);";
    private static final String QUERY_DELETE = "UPDATE `answer` SET `status` = -1 WHERE (`id` = ?) AND (`status` != -1);";

    @Override
    public Answer insert(Answer entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getQuestionId());
            statement.setLong(2, entity.getCreatorId());
            statement.setString(3, entity.getText());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long insertedId = keys.getLong(1);
                    entity = executeSelectById(connection, QUERY_SELECT_BY_ID, insertedId);
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
    public Answer selectById(long id) throws DAOException {
        Answer answer;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            answer = executeSelectById(connection, QUERY_SELECT_BY_ID, id);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return answer;
    }

    @Override
    public List<Answer> selectByQuestionId(long id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_QUESTION_ID)) {
            statement.setLong(1, id);

            return executeStatementAndParseResultSetToList(statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public void update(Answer updatedEntity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
            statement.setString(1, updatedEntity.getText());
            statement.setLong(2, updatedEntity.getId());

            statement.executeUpdate();

            Answer reselectedEntity = executeSelectById(connection, QUERY_SELECT_BY_ID, updatedEntity.getId());
            updatedEntity.setModifiedAt(reselectedEntity.getModifiedAt());
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        executeDelete(QUERY_DELETE, id);
    }

    @Override
    protected Answer parseResultSet(ResultSet resultSet) throws SQLException {
        Answer answer = new Answer();
        answer.setId(resultSet.getLong(1));
        answer.setQuestionId(resultSet.getLong(2));
        answer.setCreatorId(resultSet.getLong(3));
        answer.setText(resultSet.getString(4));
        answer.setStatus(resultSet.getInt(5));
        answer.setCreatedAt(resultSet.getTimestamp(6));
        answer.setModifiedAt(resultSet.getTimestamp(7));
        answer.setAverageMark(resultSet.getFloat(8));
        answer.setMarkCount(resultSet.getInt(9));
        answer.setCreatorUsername(resultSet.getString(10));
        return answer;
    }

}
