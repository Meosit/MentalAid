package by.mksn.epam.mentalaid.dao;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.entity.Question;

import java.util.List;

public interface AnswerDAO extends BaseDAO<Answer> {

    /**
     * Inserts new answer into database
     *
     * @param entity answer where must be set following properties:
     *               <br> - {@link Answer#creatorId} of new answer
     *               <br> - {@link Answer#questionId} of new answer
     *               <br> - {@link Answer#text} of new answer
     * @return {@link Answer} entity with all filled properties
     * @throws DAOException if something went wrong
     */
    Answer insert(Answer entity) throws DAOException;

    /**
     * Selects answer from the database with specified ID
     *
     * @param id of an entity
     * @return - {@link Answer} with the specified id <br> - {@code null} if entity not found or deleted
     * @throws DAOException if something went wrong
     */
    Answer selectById(long id) throws DAOException;

    /**
     * Selects all answers which refer to the question with specified ID<br>
     * Result list sorted by average mark descending.
     *
     * @param id id of a {@link Question}
     * @return List of all related answers
     * @throws DAOException if something went wrong
     */
    List<Answer> selectByQuestionId(long id) throws DAOException;

    /**
     * Selects count of all answers which belongs to the
     * user with specified ID
     *
     * @param id id of a user to select answer count
     * @return count of all answers which belongs to the user with specified ID
     * @throws DAOException if something went wrong
     */
    int selectCountByUserId(long id) throws DAOException;

    /**
     * Updates answer in the database with the updated one.
     * Updating by {@link Answer#id}.<br>
     * This method can affect only on {@link Answer#text} property
     *
     * @param updatedEntity entity to update
     * @throws DAOException if something went wrong
     */
    void update(Answer updatedEntity) throws DAOException;

    /**
     * Removes answer with the specified id
     *
     * @param id id of a question to delete
     * @throws DAOException if something went wrong
     */
    void delete(long id) throws DAOException;
}
