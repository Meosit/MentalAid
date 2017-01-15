package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.MarkDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.Mark;
import by.mksn.epam.mentalaid.service.MarkService;
import by.mksn.epam.mentalaid.service.exception.MarkServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class MarkServiceImpl implements MarkService {

    private static boolean isMarkValueOutOfRange(int value) {
        return value < 0 || value > MAX_MARK_VALUE;
    }

    @Override
    public void add(Mark mark) throws ServiceException {
        if (isMarkValueOutOfRange(mark.getValue())) {
            throw new MarkServiceException("Invalid mark value passed", MarkServiceException.WRONG_INPUT);
        }

        try {
            MarkDAO markDAO = DAOFactory.getDAOFactory().getMarkDAO();
            Mark oldMark = markDAO.selectByUserAndAnswerId(mark.getUserId(), mark.getAnswerId());
            if (isNull(oldMark)) {
                markDAO.insert(mark);
            } else {
                oldMark.setValue(mark.getValue());
                markDAO.update(mark);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
