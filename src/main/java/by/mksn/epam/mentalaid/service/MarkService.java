package by.mksn.epam.mentalaid.service;

import by.mksn.epam.mentalaid.entity.Mark;
import by.mksn.epam.mentalaid.service.exception.MarkServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

public interface MarkService {

    int MAX_MARK_VALUE = 5;

    /**
     * Adds new mark to an answer or updates that mark if it already exists.
     *
     * @param mark mark where must be set following properties:
     *             <br> - {@link Mark#userId}
     *             <br> - {@link Mark#answerId}
     *             <br> - {@link Mark#value}
     * @throws MarkServiceException if negative or grater than {@link #MAX_MARK_VALUE} value  passed (cause code: {@link MarkServiceException#WRONG_INPUT});
     * @throws ServiceException     if error happens during execution
     */
    void add(Mark mark) throws ServiceException;

}
