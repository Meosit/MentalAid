package by.mksn.epam.mentalaid.dao.impl;

import by.mksn.epam.mentalaid.dao.AnswerDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Answer;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static by.mksn.epam.mentalaid.util.caller.JDBCCaller.tryCallJDBC;

public class MySqlAnswerDAO extends AbstractBaseDAO<Answer> implements AnswerDAO {

    private static final Logger logger = Logger.getLogger(MySqlAnswerDAO.class);
    private static final String QUERY_INSERT = "INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (?, ?, ?);";
    private static final String QUERY_SELECT_BY_ID = "SELECT `answer`.`id`, `answer`.`question_id`, `answer`.`creator_id`, `answer`.`text`, `answer`.`status`, `answer`.`created_at`, `answer`.`modified_at`, AVG(`mark`.`value`), COUNT(`mark`.`value`), `user`.`username` FROM `answer` JOIN `user` ON `answer`.`creator_id` = `user`.`id` LEFT JOIN `mark` ON `answer`.`id` = `mark`.`answer_id` WHERE (`answer`.`id` = ?) AND (`answer`.`status` != -1) GROUP BY `answer`.`id`;";
    private static final String QUERY_SELECT_BY_QUESTION_ID = "SELECT `answer`.`id`, `answer`.`question_id`, `answer`.`creator_id`, `answer`.`text`, `answer`.`status`, `answer`.`created_at`, `answer`.`modified_at`, AVG(`mark`.`value`) AS 'averageMark', COUNT(`mark`.`value`), `user`.`username` FROM `answer` JOIN `user` ON `answer`.`creator_id` = `user`.`id` LEFT JOIN `mark` ON `answer`.`id` = `mark`.`answer_id` WHERE (`answer`.`question_id` = ?) AND (`answer`.`status` != -1) GROUP BY `answer`.`id` ORDER BY averageMark DESC, `answer`.`id`;";
    private static final String QUERY_SELECT_COUNT_BY_USER_ID = " SELECT COUNT(`answer`.`id`) FROM `answer` WHERE (`answer`.`creator_id` = ?) AND (`answer`.`status` != -1);";
    private static final String QUERY_UPDATE = "UPDATE `answer` SET `text` = ? WHERE (`id` = ?) AND (`status` != -1);";
    private static final String QUERY_DELETE = "UPDATE `answer` SET `status` = -1 WHERE (`id` = ?) AND (`status` != -1);";

    @Override
    public Answer insert(Answer entity) throws DAOException {
        return tryCallJDBC(QUERY_INSERT, (connection, statement) -> {
            statement.setLong(1, entity.getQuestionId());
            statement.setLong(2, entity.getCreatorId());
            statement.setString(3, entity.getText());
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
    public Answer selectById(long id) throws DAOException {
        return executeSelectById(QUERY_SELECT_BY_ID, id);
    }

    @Override
    public List<Answer> selectByQuestionId(long id) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_BY_QUESTION_ID, statement -> {
            statement.setLong(1, id);
            return executeStatementAndParseResultSetToList(statement);
        });
    }

    @Override
    public int selectCountByUserId(long id) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_COUNT_BY_USER_ID, statement -> {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    logger.error("Cannot select answer count, empty result set.");
                    throw new DAOException("Cannot select answer count");
                }
            }
        });
    }

    @Override
    public void update(Answer updatedEntity) throws DAOException {
        tryCallJDBC(QUERY_UPDATE, (connection, statement) -> {
            statement.setString(1, updatedEntity.getText());
            statement.setLong(2, updatedEntity.getId());
            statement.executeUpdate();
            Answer reselectedEntity = executeSelectById(connection, QUERY_SELECT_BY_ID, updatedEntity.getId());
            updatedEntity.setModifiedAt(reselectedEntity.getModifiedAt());
        });
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
