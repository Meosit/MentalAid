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
    public Mark add(Mark mark) throws ServiceException {
        if (isMarkValueOutOfRange(mark.getValue())) {
            throw new MarkServiceException("Invalid mark value passed", MarkServiceException.WRONG_INPUT);
        }

        try {
            MarkDAO markDAO = DAOFactory.getDAOFactory().getMarkDAO();
            mark = markDAO.insert(mark);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return mark;
    }

    @Override
    public Mark getById(long id) throws ServiceException {
        Mark mark;
        try {
            MarkDAO markDAO = DAOFactory.getDAOFactory().getMarkDAO();
            mark = markDAO.selectById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return mark;
    }

    @Override
    public void update(Mark updatedMark) throws ServiceException {
        if (isMarkValueOutOfRange(updatedMark.getValue())) {
            throw new MarkServiceException("Invalid mark value passed", MarkServiceException.WRONG_INPUT);
        }

        try {
            MarkDAO markDAO = DAOFactory.getDAOFactory().getMarkDAO();
            markDAO.update(updatedMark);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isMarkAlreadyExists(long answerId, long userId) throws ServiceException {
        Mark mark;
        try {
            MarkDAO markDAO = DAOFactory.getDAOFactory().getMarkDAO();
            mark = markDAO.selectByUserAndAnswerId(userId, answerId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return !isNull(mark);
    }
}
