package by.mksn.epam.mentalaid.dao;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.entity.User;

import java.util.List;

public interface QuestionDAO extends BaseDAO<Question> {

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
    Question insert(Question entity) throws DAOException;

    /**
     * Selects question from the database with specified ID
     *
     * @param id of an entity
     * @return - {@link Question} with the specified id <br> - {@code null} if entity not found or deleted
     * @throws DAOException if something went wrong
     */
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
    List<Question> selectWithLimit(int offset, int count) throws DAOException;

    /**
     * Selects list of questions belonging to user with specified ID and with limit constraints.<br>
     *     Result list sorted by create date descending.<br>
     *         All questions is not deleted.
     *
     * @param offset offset of the first question in selected list (0 means from the beginning)
     * @param count count of questions to select (result list size may be less than specified count)
     * @return list of {@link Question} with the specified limit of user with the specified username
     * @throws DAOException if something went wrong
     */
    List<Question> selectByUsernameWithLimit(String username, int offset, int count) throws DAOException;

    /**
     * Selects count of all non-deleted questions
     *
     * @return count of all non-deleted questions
     * @throws DAOException if something went wrong
     */
    int selectCount() throws DAOException;

    /**
     * Selects count of all non-deleted questions which belongs to usern with the specified username
     *
     * @param username username of {@link User}
     * @return count of all non-deleted questions
     * @throws DAOException if something went wrong
     */
    int selectCountByUsername(String username) throws DAOException;

    /**
     * Updates question in the database with the updated one.
     * Updating by {@link Question#id}.<br>
     * This method can affect only on {@link Question#title} and {@link Question#description} properties
     *
     * @param updatedEntity entity to update
     * @throws DAOException if something went wrong
     */
    void update(Question updatedEntity) throws DAOException;

    /**
     * Removes question with the specified id
     *
     * @param id id of a question to delete
     * @throws DAOException if something went wrong
     */
    void delete(long id) throws DAOException;

}
