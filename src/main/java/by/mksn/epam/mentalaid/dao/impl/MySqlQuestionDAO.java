package by.mksn.epam.mentalaid.dao.impl;

import by.mksn.epam.mentalaid.dao.QuestionDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Question;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static by.mksn.epam.mentalaid.util.caller.JDBCCaller.tryCallJDBC;

/**
 * MySQL implementation of {@link QuestionDAO}
 */
public class MySqlQuestionDAO extends AbstractBaseDAO<Question> implements QuestionDAO {

    private static final Logger logger = Logger.getLogger(MySqlQuestionDAO.class);
    private static final String QUERY_INSERT = "INSERT INTO `question` (`creator_id`, `title`, `description`) VALUES (?, ?, ?);";
    private static final String QUERY_SELECT_BY_ID = "SELECT `question`.`id`, `question`.`creator_id`, `question`.`title`, `question`.`description`, `question`.`status`, `question`.`created_at`, `question`.`modified_at`, `user`.`username`, COUNT(`answer`.`question_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` LEFT JOIN `answer` ON (`question`.`id` = `answer`.`question_id`) AND (`answer`.`status` != -1) WHERE (`question`.`id` = ?) AND (`question`.`status` != -1) GROUP BY `question`.`id`;";
    private static final String QUERY_SELECT_WITH_LIMIT = "SELECT `question`.`id`, `question`.`creator_id`, `question`.`title`, `question`.`description`, `question`.`status`, `question`.`created_at`, `question`.`modified_at`, `user`.`username`, COUNT(`answer`.`question_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` LEFT JOIN `answer` ON (`question`.`id` = `answer`.`question_id`) AND (`answer`.`status` != -1) WHERE (`question`.`status` != -1) GROUP BY `question`.`id` ORDER BY `question`.`id` DESC LIMIT ?, ?;";
    private static final String QUERY_SELECT_LIKE_WITH_LIMIT = "SELECT `question`.`id`, `question`.`creator_id`, `question`.`title`, `question`.`description`, `question`.`status`, `question`.`created_at`, `question`.`modified_at`, `user`.`username`, COUNT(`answer`.`question_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` LEFT JOIN `answer` ON (`question`.`id` = `answer`.`question_id`) AND (`answer`.`status` != -1) WHERE (`question`.`status` != -1) AND ((LOWER(`question`.`description`) LIKE LOWER(?)) OR (LOWER(`question`.`title`) LIKE LOWER(?))) GROUP BY `question`.`id` ORDER BY `question`.`id` DESC LIMIT ?, ?;";
    private static final String QUERY_SELECT_BY_USERNAME_WITH_LIMIT = "SELECT `question`.`id`, `question`.`creator_id`, `question`.`title`, `question`.`description`, `question`.`status`, `question`.`created_at`, `question`.`modified_at`, `user`.`username`, COUNT(`answer`.`question_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` LEFT JOIN `answer` ON (`question`.`id` = `answer`.`question_id`) AND (`answer`.`status` != -1) WHERE (`user`.`username` = ?) AND (`question`.`status` != -1) GROUP BY `question`.`id` ORDER BY `question`.`id` DESC LIMIT ?, ?;";
    private static final String QUERY_SELECT_LIKE_BY_USERNAME_WITH_LIMIT = "SELECT `question`.`id`, `question`.`creator_id`, `question`.`title`, `question`.`description`, `question`.`status`, `question`.`created_at`, `question`.`modified_at`, `user`.`username`, COUNT(`answer`.`question_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` LEFT JOIN `answer` ON (`question`.`id` = `answer`.`question_id`) AND (`answer`.`status` != -1) WHERE (`user`.`username` = ?) AND (`question`.`status` != -1) AND ((LOWER(`question`.`description`) LIKE LOWER(?)) OR (LOWER(`question`.`title`) LIKE LOWER(?))) GROUP BY `question`.`id` ORDER BY `question`.`id` DESC LIMIT ?, ?;";
    private static final String QUERY_SELECT_COUNT = "SELECT COUNT(`id`) FROM `question` WHERE `question`.`status` != -1;";
    private static final String QUERY_SELECT_LIKE_COUNT = "SELECT COUNT(`id`) FROM `question` WHERE (`question`.`status` != -1) AND ((LOWER(`question`.`description`) LIKE LOWER(?)) OR (LOWER(`question`.`title`) LIKE LOWER(?)));";
    private static final String QUERY_SELECT_COUNT_BY_USERNAME = "SELECT COUNT(`creator_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` WHERE (`user`.`username` = ?) AND (`question`.`status` != -1);";
    private static final String QUERY_SELECT_LIKE_COUNT_BY_USERNAME = "SELECT COUNT(`creator_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` WHERE (`user`.`username` = ?) AND (`question`.`status` != -1) AND ((LOWER(`question`.`description`) LIKE LOWER(?)) OR (LOWER(`question`.`title`) LIKE LOWER(?)));";
    private static final String QUERY_UPDATE = "UPDATE `question` SET `title` = ?, `description` = ? WHERE (`id` = ?) AND (`status` != -1);";
    private static final String QUERY_DELETE = "UPDATE `question` SET `status` = -1 WHERE (`id` = ?) AND (`status` != -1);";

    @Override
    public Question insert(Question entity) throws DAOException {
        return tryCallJDBC(QUERY_INSERT, (connection, statement) -> {
            statement.setLong(1, entity.getCreatorId());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getDescription());
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
    public Question selectById(long id) throws DAOException {
        return executeSelectById(QUERY_SELECT_BY_ID, id);
    }

    @Override
    public List<Question> selectWithLimit(int offset, int count) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_WITH_LIMIT, statement -> {
            statement.setInt(1, offset);
            statement.setInt(2, count);
            return executeStatementAndParseResultSetToList(statement);
        });
    }

    @Override
    public List<Question> selectLikeWithLimit(String likeQuery, int offset, int count) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_LIKE_WITH_LIMIT, statement -> {
            String likePattern = createGlobalLikePattern(likeQuery);
            statement.setString(1, likePattern);
            statement.setString(2, likePattern);
            statement.setInt(3, offset);
            statement.setInt(4, count);
            return executeStatementAndParseResultSetToList(statement);
        });
    }

    @Override
    public List<Question> selectByUsernameWithLimit(String username, int offset, int count) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_BY_USERNAME_WITH_LIMIT, statement -> {
            statement.setString(1, username);
            statement.setInt(2, offset);
            statement.setInt(3, count);
            return executeStatementAndParseResultSetToList(statement);
        });
    }

    @Override
    public List<Question> selectLikeByUsernameWithLimit(String likeQuery, String username, int offset, int count) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_LIKE_BY_USERNAME_WITH_LIMIT, statement -> {
            String likePattern = createGlobalLikePattern(likeQuery);
            statement.setString(1, username);
            statement.setString(2, likePattern);
            statement.setString(3, likePattern);
            statement.setInt(4, offset);
            statement.setInt(5, count);
            return executeStatementAndParseResultSetToList(statement);
        });
    }

    @Override
    public int selectCount() throws DAOException {
        return tryCallJDBC(QUERY_SELECT_COUNT, statement -> {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    logger.error("Cannot select question count, empty result set.");
                    throw new DAOException("Cannot select question count");
                }
            }
        });
    }

    @Override
    public int selectLikeCount(String likeQuery) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_LIKE_COUNT, statement -> {
            String likePattern = createGlobalLikePattern(likeQuery);
            statement.setString(1, likePattern);
            statement.setString(2, likePattern);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    logger.error("Cannot select question count, empty result set.");
                    throw new DAOException("Cannot select question count");
                }
            }
        });
    }

    @Override
    public int selectCountByUsername(String username) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_COUNT_BY_USERNAME, statement -> {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    logger.error("Cannot select question count, empty result set.");
                    throw new DAOException("Cannot select question count");
                }
            }
        });
    }

    @Override
    public int selectLikeCountByUsername(String likeQuery, String username) throws DAOException {
        return tryCallJDBC(QUERY_SELECT_LIKE_COUNT_BY_USERNAME, statement -> {
            String likePattern = createGlobalLikePattern(likeQuery);
            statement.setString(1, username);
            statement.setString(2, likePattern);
            statement.setString(3, likePattern);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    logger.error("Cannot select question count, empty result set.");
                    throw new DAOException("Cannot select question count");
                }
            }
        });
    }

    @Override
    public void update(Question updatedEntity) throws DAOException {
        tryCallJDBC(QUERY_UPDATE, ((connection, statement) -> {
            statement.setString(1, updatedEntity.getTitle());
            statement.setString(2, updatedEntity.getDescription());
            statement.setLong(3, updatedEntity.getId());
            statement.executeUpdate();

            Question reselectedEntity = executeSelectById(connection, QUERY_SELECT_BY_ID, updatedEntity.getId());
            updatedEntity.setModifiedAt(reselectedEntity.getModifiedAt());
        }));
    }

    @Override
    public void delete(long id) throws DAOException {
        executeDelete(QUERY_DELETE, id);
    }

    @Override
    protected Question parseResultSet(ResultSet resultSet) throws SQLException {
        Question question = new Question();
        question.setId(resultSet.getLong(1));
        question.setCreatorId(resultSet.getLong(2));
        question.setTitle(resultSet.getString(3));
        question.setDescription(resultSet.getString(4));
        question.setStatus(resultSet.getInt(5));
        question.setCreatedAt(resultSet.getTimestamp(6));
        question.setModifiedAt(resultSet.getTimestamp(7));
        question.setCreatorUsername(resultSet.getString(8));
        question.setAnswerCount(resultSet.getInt(9));
        return question;
    }

}
