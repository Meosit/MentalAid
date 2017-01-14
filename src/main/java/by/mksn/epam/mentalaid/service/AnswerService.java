package by.mksn.epam.mentalaid.service;

import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.service.exception.AnswerServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

import java.util.List;

public interface AnswerService {

    /**
     * Adds new answer
     *
     * @param answer answer where must be set following properties:
     *               <br> - {@link Answer#creatorId} of new answer
     *               <br> - {@link Answer#questionId} of new answer
     *               <br> - {@link Answer#text} of new answer
     * @return {@link Answer} entity with all filled properties
     * @throws AnswerServiceException if null or empty text passed (cause code: {@link AnswerServiceException#WRONG_INPUT});
     * @throws ServiceException       if error happens during execution
     */
    Answer add(Answer answer) throws ServiceException;

    /**
     * Returns {@link Answer} with the specified ID
     *
     * @param id id of an answer
     * @return {@link Answer} with the specified ID, {@code null} if such answer is not exist
     * @throws ServiceException if error happens during execution
     */
    Answer getById(long id) throws ServiceException;

    /**
     * Returns all answers for the question with specified ID
     *
     * @param questionId id of a question this answers belongs to
     * @return List of answers, sorted by {@link Answer#averageMark} and {@link Answer#createdAt} properties descending
     * @throws ServiceException if error happens during execution
     */
    List<Answer> getAnswersForQuestion(long questionId) throws ServiceException;

    /**
     * Updates answer data, affects only on
     * {@link Answer#text} property.<br>
     * This method also update {@link Answer#modifiedAt} property.<br><br>
     * Updating based on {@link Answer#id} property.
     *
     * @param updatedAnswer {@link Answer} with already updated properties
     * @throws AnswerServiceException if null or empty text passed (cause code: {@link AnswerServiceException#WRONG_INPUT});
     * @throws ServiceException if error happens during execution
     */
    void update(Answer updatedAnswer) throws ServiceException;

    /**
     * Deletes answer with the specified id from the system.<br>
     * Does nothing if answer with this id doesn't exist.
     *
     * @param id of the {@link Answer} to delete
     * @throws ServiceException if error happens during execution
     */
    void delete(long id) throws ServiceException;

    /**
     * Checks is user with specified id already has an answer for a question with specified id.
     *
     * @param questionId id of a question for search appropriate answers
     * @param userId     id of a user to search answers which this user created
     * @return {@code true} if the answer already exists, {@code false} otherwise
     * @throws ServiceException if error happens during execution
     */
    default boolean isAnswerAlreadyExists(long questionId, long userId) throws ServiceException {
        return getAnswersForQuestion(questionId).stream().anyMatch(x -> x.getCreatorId() == userId);
    }
}
