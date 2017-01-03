package by.mksn.epam.mentalaid.service;

import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.service.exception.QuestionServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

import java.util.List;

public interface QuestionService {

    /**
     * Default question count on one page, may be in future will be added user preference on this
     */
    int QUESTIONS_PER_PAGE = 10;

    /**
     * Adds new question
     *
     * @param question question where must be set following properties:
     *                 <br> - {@link Question#creatorId} of new question
     *                 <br> - {@link Question#title} of new question
     *                 <br> - {@link Question#description} of new question
     * @return {@link Question} entity with all filled properties
     * @throws QuestionServiceException if null title or description passed (cause code: {@link QuestionServiceException#WRONG_INPUT});
     * @throws ServiceException         if error happens during execution
     */
    Question add(Question question) throws ServiceException;

    /**
     * Returns {@link Question} with the specified ID
     *
     * @param id id of a question
     * @return {@link Question} with the specified ID
     * @throws ServiceException if error happens during execution
     */
    Question getById(long id) throws ServiceException;

    /**
     * Returns list of {@link Question} on the page with specified index.
     *
     * @param index            index of page to return, must be positive and {@code index * questionsPerPage <= 2147483647}
     * @param questionsPerPage count of questions on the page
     * @return list of {@link Question} on the page with specified index
     * @throws QuestionServiceException if invalid page index passed (cause code: {@link QuestionServiceException#INVALID_PAGE_INDEX})
     * @throws ServiceException         if error happens during execution
     */
    List<Question> getQuestionsPage(int index, int questionsPerPage) throws ServiceException;

    /**
     * Does same as {@link #getQuestionsPage(int, int)}, but with
     * {@link #QUESTIONS_PER_PAGE} as second parameter.
     *
     * @see #getQuestionsPage(int, int)
     */
    default List<Question> getQuestionsPage(int index) throws ServiceException {
        return getQuestionsPage(index, QUESTIONS_PER_PAGE);
    }

    /**
     * Returns list of {@link Question} on the page with specified index which belongs
     * to user with specified username
     *
     * @param username         username of user this questions belongs to
     * @param index            index of page to return, must be positive and {@code index * questionsPerPage <= 2147483647}
     * @param questionsPerPage count of questions on the page
     * @return list of {@link Question} on the page with specified index
     * @throws QuestionServiceException if invalid page index passed (cause code: {@link QuestionServiceException#INVALID_PAGE_INDEX})
     * @throws ServiceException         if error happens during execution
     */
    List<Question> getQuestionsPage(String username, int index, int questionsPerPage) throws ServiceException;

    /**
     * Does same as {@link #getQuestionsPage(String, int, int)}, but with
     * {@link #QUESTIONS_PER_PAGE} as third parameter.
     *
     * @see #getQuestionsPage(String, int, int)
     */
    default List<Question> getQuestionsPage(String username, int index) throws ServiceException {
        return getQuestionsPage(username, index, QUESTIONS_PER_PAGE);
    }

    /**
     * Returns page count, calculated on base of {@code questionPerPage} parameter
     * and on count of all question in the system
     *
     * @param questionsPerPage count of questions on the page
     * @return count of pages
     * @throws ServiceException if error happens during execution
     */
    int getPageCount(int questionsPerPage) throws ServiceException;

    /**
     * Does same as {@link #getPageCount(int)}, but with
     * {@link #QUESTIONS_PER_PAGE} a parameter.
     *
     * @see #getPageCount(int)
     */
    default int getPageCount() throws ServiceException {
        return getPageCount(QUESTIONS_PER_PAGE);
    }

    /**
     * Returns page count, calculated on base of {@code questionPerPage} parameter
     * and on count of all question in the system which belongs to user with specified username
     *
     * @param username         username of a user this question belongs to
     * @param questionsPerPage count of questions on the page
     * @return count of pages
     * @throws ServiceException if error happens during execution
     */
    int getPageCount(String username, int questionsPerPage) throws ServiceException;

    /**
     * Does same as {@link #getPageCount(String, int)}, but with
     * {@link #QUESTIONS_PER_PAGE} a parameter.
     *
     * @see #getPageCount(String, int)
     */
    default int getPageCount(String username) throws ServiceException {
        return getPageCount(username, QUESTIONS_PER_PAGE);
    }

    /**
     * Updates question data, affects only on
     * {@link Question#title} and {@link Question#description} properties.<br>
     * This method also update {@link Question#modifiedAt} property.<br><br>
     * Updating based on {@link Question#id} property.
     *
     * @param updatedQuestion {@link Question} with already updated properties
     * @throws QuestionServiceException if trying to update deleted question (cause code: {@link QuestionServiceException#ENTITY_DELETED})
     * @throws ServiceException         if error happens during execution
     */
    void update(Question updatedQuestion) throws ServiceException;

    /**
     * Deletes question with the specified id from the system.<br>
     * Does nothing if question with this id doesn't exist.
     *
     * @param id of the {@link Question} to delete
     * @throws ServiceException if error happens during execution
     */
    void delete(long id) throws ServiceException;

}
