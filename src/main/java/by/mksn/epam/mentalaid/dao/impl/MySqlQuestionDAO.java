package by.mksn.epam.mentalaid.dao.impl;

import by.mksn.epam.mentalaid.dao.QuestionDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.pool.ConnectionPool;
import by.mksn.epam.mentalaid.dao.pool.exception.PoolException;
import by.mksn.epam.mentalaid.entity.Question;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * MySQL implementation of {@link QuestionDAO}
 */
public class MySqlQuestionDAO extends AbstractBaseDAO<Question> implements QuestionDAO {

    private static final Logger logger = Logger.getLogger(MySqlQuestionDAO.class);
    private static final String QUERY_INSERT = "INSERT INTO `question` (`creator_id`, `title`, `description`) VALUES (?, ?, ?);";
    private static final String QUERY_SELECT_BY_ID = "SELECT `question`.`id`, `question`.`creator_id`, `question`.`title`, `question`.`description`, `question`.`status`, `question`.`created_at`, `question`.`modified_at`, `user`.`username`, COUNT(`answer`.`question_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` LEFT JOIN `answer` ON `question`.`id` = `answer`.`question_id` WHERE (`question`.`id` = ?) AND (`question`.`status` != -1) GROUP BY `question`.`id`;";
    private static final String QUERY_SELECT_WITH_LIMIT = "SELECT `question`.`id`, `question`.`creator_id`, `question`.`title`, `question`.`description`, `question`.`status`, `question`.`created_at`, `question`.`modified_at`, `user`.`username`, COUNT(`answer`.`question_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` LEFT JOIN `answer` ON `question`.`id` = `answer`.`question_id` WHERE `question`.`status` != -1 GROUP BY `question`.`id` ORDER BY `question`.`id` DESC LIMIT ?, ?;";
    private static final String QUERY_SELECT_BY_USERNAME_WITH_LIMIT = "SELECT `question`.`id`, `question`.`creator_id`, `question`.`title`, `question`.`description`, `question`.`status`, `question`.`created_at`, `question`.`modified_at`, `user`.`username`, COUNT(`answer`.`question_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` LEFT JOIN `answer` ON `question`.`id` = `answer`.`question_id` WHERE (`user`.`username` = ?) AND (`question`.`status` != -1)  GROUP BY `question`.`id` ORDER BY `question`.`id` DESC LIMIT ?, ?;";
    private static final String QUERY_SELECT_COUNT = "SELECT COUNT(`id`) FROM `question` WHERE `question`.`status` != -1;";
    private static final String QUERY_SELECT_COUNT_BY_USERNAME = "SELECT COUNT(`creator_id`) FROM `question` JOIN `user` ON `question`.`creator_id` = `user`.`id` WHERE (`user`.`username` = ?) AND (`question`.`status` != -1);";
    private static final String QUERY_UPDATE = "UPDATE `question` SET `title` = ?, `description` = ? WHERE (`id` = ?) AND (`status` != -1);";
    private static final String QUERY_DELETE = "UPDATE `question` SET `status` = -1 WHERE `id` = ?;";

    @Override
    public Question insert(Question entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getCreatorId());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getDescription());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long insertedId = keys.getLong(1);
                entity = selectById(connection, insertedId);
            } else {
                throw new DAOException("Generated keys set is empty");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return entity;
    }

    @Override
    public Question selectById(long id) throws DAOException {
        Question question;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            question = selectById(connection, id);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
        return question;
    }

    @Override
    public List<Question> selectWithLimit(int offset, int count) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_WITH_LIMIT)) {
            statement.setInt(1, offset);
            statement.setInt(2, count);

            return executeStatementAndParseResultSetToList(statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public List<Question> selectByUsernameWithLimit(String username, int offset, int count) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_USERNAME_WITH_LIMIT)) {
            statement.setString(1, username);
            statement.setInt(2, offset);
            statement.setInt(3, count);

            return executeStatementAndParseResultSetToList(statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public int selectCount() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_COUNT);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("Cannot select question count, empty result set.");
                throw new DAOException("Cannot select question count");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public int selectCountByUsername(String username) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_COUNT_BY_USERNAME)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    logger.error("Cannot select question count, empty result set.");
                    throw new DAOException("Cannot select question count");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (PoolException e) {
            throw new DAOException("Cannot get connection\n", e);
        }
    }

    @Override
    public void update(Question updatedEntity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
            statement.setString(1, updatedEntity.getTitle());
            statement.setString(2, updatedEntity.getDescription());
            statement.setLong(3, updatedEntity.getId());

            statement.executeUpdate();
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

    private Question selectById(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setLong(1, id);
            return executeStatementAndParseResultSet(statement);
        }
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
