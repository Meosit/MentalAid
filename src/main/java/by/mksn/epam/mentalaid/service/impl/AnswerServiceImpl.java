package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.AnswerDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.exception.AnswerServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

import java.util.List;

import static by.mksn.epam.mentalaid.util.StringUtil.*;
import static by.mksn.epam.mentalaid.util.caller.DAOCaller.tryCallDAO;

public class AnswerServiceImpl implements AnswerService {

    private static final int TEXT_MAX_LENGTH = 2000;

    @Override
    public Answer add(Answer answer) throws ServiceException {
        if (isNullOrEmpty(answer.getText())) {
            throw new AnswerServiceException("Null text passed", AnswerServiceException.WRONG_INPUT);
        }

        String normalizedText = trimAndCollapseNewLines(answer.getText());
        normalizedText = truncateToSize(normalizedText, TEXT_MAX_LENGTH);
        answer.setText(normalizedText);

        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        return tryCallDAO(() -> answerDAO.insert(answer));
    }

    @Override
    public Answer getById(long id) throws ServiceException {
        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        return tryCallDAO(() -> answerDAO.selectById(id));
    }

    @Override
    public int getCountByUserId(long userId) throws ServiceException {
        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        return tryCallDAO(() -> answerDAO.selectCountByUserId(userId));
    }

    @Override
    public List<Answer> getAnswersForQuestion(long questionId) throws ServiceException {
        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        return tryCallDAO(() -> answerDAO.selectByQuestionId(questionId));
    }

    @Override
    public void update(Answer updatedAnswer) throws ServiceException {
        if (isNullOrEmpty(updatedAnswer.getText())) {
            throw new AnswerServiceException("Null text passed", AnswerServiceException.WRONG_INPUT);
        }

        String normalizedText = trimAndCollapseNewLines(updatedAnswer.getText());
        normalizedText = truncateToSize(normalizedText, TEXT_MAX_LENGTH);
        updatedAnswer.setText(normalizedText);

        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        tryCallDAO(() -> answerDAO.update(updatedAnswer));
    }

    @Override
    public void delete(long id) throws ServiceException {
        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        tryCallDAO(() -> answerDAO.delete(id));
    }
}
