package by.mksn.epam.mentalaid.dao;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.entity.User;

import java.util.List;

public interface QuestionDAO extends BaseDAO<Question>, EntityListDAO<Question> {

    /**
     * Inserts new question into database
     *
     * @param entity question where must be set following properties:
     *               <br> - {@link Question#creatorId} of new question
     *               <br> - {@link Question#title} of new question
     *               <br> - {@link Question#description} of new question
     * @return {@link Question} entity with all filled properties
     * @throws DAOException if something went wrong
     */
    @Override
    Question insert(Question entity) throws DAOException;

    /**
     * Selects question from the database with specified ID
     *
     * @param id of an entity
     * @return - {@link Question} with the specified id <br> - {@code null} if entity not found or deleted
     * @throws DAOException if something went wrong
     */
    @Override
    Question selectById(long id) throws DAOException;

    /**
     * Selects list of questions with limit constraints.<br>
     * Result list sorted by create date descending.<br>
     * All questions is not deleted.
     *
     * @param offset offset of the first question in selected list (0 means from the beginning)
     * @param count  count of questions to select (result list size may be less than specified count)
     * @return list of {@link Question} with the specified limit
     * @throws DAOException if something went wrong
     */
    @Override
    List<Question> selectWithLimit(int offset, int count) throws DAOException;

    /**
     * Selects list of questions with LIKE constraint for `description`
     * and `title` columns. Also limit constraints provided.<br>
     * Result list sorted by create date descending.<br>
     * All questions is not deleted.
     *
     * @param likeQuery query for like select
     * @param offset offset of the first question in selected list (0 means from the beginning)
     * @param count  count of questions to select (result list size may be less than specified count)
     * @return list of {@link Question} with the specified limit
     * @throws DAOException if something went wrong
     */
    @Override
    List<Question> selectLikeWithLimit(String likeQuery, int offset, int count) throws DAOException;

    /**
     * Selects list of questions belonging to user with specified username and with limit constraints.<br>
     *     Result list sorted by create date descending.<br>
     *         All questions is not deleted.
     *
     * @param username username of user to select
     * @param offset offset of the first question in selected list (0 means from the beginning)
     * @param count count of questions to select (result list size may be less than specified count)
     * @return list of {@link Question} with the specified limit of user with the specified username
     * @throws DAOException if something went wrong
     */
    List<Question> selectByUsernameWithLimit(String username, int offset, int count) throws DAOException;

    /**
     * Selects list of questions belonging to user with specified username
     * with LIKE constraint for `description` and `title` columns.<br>
     * Selecting is case-insensitive
     * Also limit constraints provided.<br>
     * Result list sorted by create date descending.<br>
     * All questions is not deleted.
     *
     * @param likeQuery query for like select
     * @param username  username of user to select
     * @param offset    offset of the first question in selected list (0 means from the beginning)
     * @param count     count of questions to select (result list size may be less than specified count)
     * @return list of {@link Question} with the specified limit
     * @throws DAOException if something went wrong
     */
    List<Question> selectLikeByUsernameWithLimit(String likeQuery, String username, int offset, int count) throws DAOException;

    /**
     * Selects count of all non-deleted questions
     *
     * @return count of all non-deleted questions
     * @throws DAOException if something went wrong
     */
    @Override
    int selectCount() throws DAOException;

    /**
     * Selects count of all non-deleted questions with
     * LIKE constraint for `description` and `title` columns.
     *
     * @param likeQuery query for like select
     * @return count of all non-deleted questions
     * @throws DAOException if something went wrong
     */
    @Override
    int selectLikeCount(String likeQuery) throws DAOException;

    /**
     * Selects count of all non-deleted questions which belongs to user with the specified username
     *
     * @param username username of {@link User}
     * @return count of all non-deleted questions
     * @throws DAOException if something went wrong
     */
    int selectCountByUsername(String username) throws DAOException;

    /**
     * Selects count of all non-deleted questions which belongs to user with the specified username
     * and with LIKE constraint for `description` and `title` columns.
     *
     * @param likeQuery query for like select
     * @param username  username of {@link User}
     * @return count of all non-deleted questions
     * @throws DAOException if something went wrong
     */
    int selectLikeCountByUsername(String likeQuery, String username) throws DAOException;

    /**
     * Updates question in the database with the updated one.
     * Updating by {@link Question#id}.<br>
     * This method can affect only on {@link Question#title} and {@link Question#description} properties
     *
     * @param updatedEntity entity to update
     * @throws DAOException if something went wrong
     */
    @Override
    void update(Question updatedEntity) throws DAOException;

    /**
     * Removes question with the specified id
     *
     * @param id id of a question to delete
     * @throws DAOException if something went wrong
     */
    @Override
    void delete(long id) throws DAOException;

}
