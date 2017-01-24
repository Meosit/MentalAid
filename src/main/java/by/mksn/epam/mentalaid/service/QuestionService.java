package by.mksn.epam.mentalaid.service;

import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.service.exception.QuestionServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public interface QuestionService extends ItemPageService<Question> {

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
     * @throws QuestionServiceException if null/empty title or description passed (cause code: {@link QuestionServiceException#WRONG_INPUT});
     * @throws ServiceException         if error happens during execution
     */
    Question add(Question question) throws ServiceException;

    /**
     * Returns {@link Question} with the specified ID
     *
     * @param id id of a question
     * @return {@link Question} with the specified ID, {@code null} if such question is not exist
     * @throws ServiceException if error happens during execution
     */
    Question getById(long id) throws ServiceException;

    /**
     * Return count of questions of the user with specified username
     *
     * @param username username of user
     * @return count of user questions
     * @throws ServiceException if error happens during execution
     */
    int getUserQuestionCount(String username) throws ServiceException;


    /**
     * Selects page with specified index of questions which created user with such username and maximum count of
     * items on the single page equals to {@link #ITEMS_PER_PAGE}
     *
     * @param username username of a user
     * @param index index of the page
     * @return {@link ItemsPage} entity of the current page
     * @throws ServiceException if error happens during execution
     */
    ItemsPage<Question> getPageForUser(String username, int index) throws ServiceException;

    /**
     * Selects page of searched questions of the user according searchQuery with specified index
     * and maximum count of items on the single page equals to {@link #ITEMS_PER_PAGE}
     *
     * @param username username of a user
     * @param index index of the page
     * @param searchQuery query to search
     * @return {@link ItemsPage} entity of the current page
     * @throws ServiceException if error happens during execution
     */
    ItemsPage<Question> getSearchPageForUser(String username, int index, String searchQuery) throws ServiceException;

    /**
     * Updates question data, affects only on
     * {@link Question#title} and {@link Question#description} properties.<br>
     * This method also update {@link Question#modifiedAt} property.<br><br>
     * Updating based on {@link Question#id} property.
     *
     * @param updatedQuestion {@link Question} with already updated properties
     * @throws QuestionServiceException if null/empty title or description passed (cause code: {@link QuestionServiceException#WRONG_INPUT})
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

    /**
     * Checks is question with the specified id exists in the database
     *
     * @param id id of a {@link Question} to check;
     * @return {@code true} if the question with specified ID exists, {@code false} otherwise
     * @throws ServiceException if error happens during execution
     */
    default boolean isQuestionExists(long id) throws ServiceException {
        return !isNull(getById(id));
    }

}
