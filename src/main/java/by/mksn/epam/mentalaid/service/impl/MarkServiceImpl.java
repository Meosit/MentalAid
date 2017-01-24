package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.MarkDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.Mark;
import by.mksn.epam.mentalaid.service.MarkService;
import by.mksn.epam.mentalaid.service.exception.MarkServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
import static by.mksn.epam.mentalaid.util.caller.DAOCaller.tryCallDAO;

public class MarkServiceImpl implements MarkService {

    private static boolean isMarkValueOutOfRange(int value) {
        return value < 0 || value > MAX_MARK_VALUE;
    }

    private MarkDAO getMarkDAO() {
        return DAOFactory.getDAOFactory().getMarkDAO();
    }

    @Override
    public int add(Mark mark) throws ServiceException {
        if (isMarkValueOutOfRange(mark.getValue())) {
            throw new MarkServiceException("Invalid mark value passed", MarkServiceException.WRONG_INPUT);
        }

        return tryCallDAO(() -> {
            Mark oldMark = getMarkDAO().selectByUserAndAnswerId(mark.getUserId(), mark.getAnswerId());
            int delta;
            if (isNull(oldMark)) {
                delta = MAX_MARK_VALUE + 1;
                getMarkDAO().insert(mark);
            } else {
                delta = oldMark.getValue() - mark.getValue();
                oldMark.setValue(mark.getValue());
                getMarkDAO().update(oldMark);
            }
            return delta;
        });
    }

}
