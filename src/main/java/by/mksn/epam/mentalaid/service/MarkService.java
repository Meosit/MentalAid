package by.mksn.epam.mentalaid.service;

import by.mksn.epam.mentalaid.entity.Mark;
import by.mksn.epam.mentalaid.service.exception.MarkServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

public interface MarkService {

    int MAX_MARK_VALUE = 5;

    /**
     * Adds new mark to an answer
     *
     * @param mark mark where must be set following properties:
     *             <br> - {@link Mark#userId} of new mark
     *             <br> - {@link Mark#answerId} of new mark
     *             <br> - {@link Mark#value} of new mark
     * @return {@link Mark} entity with all filled properties
     * @throws MarkServiceException if negative or grater than {@link #MAX_MARK_VALUE} value  passed (cause code: {@link MarkServiceException#WRONG_INPUT});
     * @throws ServiceException     if error happens during execution
     */
    Mark add(Mark mark) throws ServiceException;

    /**
     * Returns {@link Mark} with the specified ID
     *
     * @param id id of an mark
     * @return {@link Mark} with the specified ID, {@code null} if such mark is not exist
     * @throws ServiceException if error happens during execution
     */
    Mark getById(long id) throws ServiceException;

    /**
     * Updates answer data, affects only on
     * {@link Mark#value} property.<br>
     * This method also update {@link Mark#modifiedAt} property.<br><br>
     * Updating based on {@link Mark#id} property.
     *
     * @param updatedMark{@link Answer} with already updated properties
     * @throws MarkServiceException if negative or grater than {@link #MAX_MARK_VALUE} value  passed (cause code: {@link MarkServiceException#WRONG_INPUT});
     * @throws ServiceException     if error happens during execution
     */
    void update(Mark updatedMark) throws ServiceException;

    /**
     * Checks is user with specified id already has mark for an answer with specified id.
     *
     * @param answerId id of an answer for search appropriate marks
     * @param userId   id of a user to search marks which this user created
     * @return {@code true} if the mark already exists, {@code false} otherwise
     * @throws ServiceException if error happens during execution
     */
    boolean isMarkAlreadyExists(long answerId, long userId) throws ServiceException;
}
