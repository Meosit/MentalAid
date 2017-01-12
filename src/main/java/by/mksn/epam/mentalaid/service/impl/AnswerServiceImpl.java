package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.AnswerDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.exception.AnswerServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

import java.util.List;

import static by.mksn.epam.mentalaid.util.StringUtil.*;

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
        try {
            answer = answerDAO.insert(answer);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return answer;
    }

    @Override
    public Answer getById(long id) throws ServiceException {
        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        Answer answer;
        try {
            answer = answerDAO.selectById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return answer;
    }

    @Override
    public List<Answer> getAnswersForQuestion(long questionId) throws ServiceException {
        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        List<Answer> answers;
        try {
            answers = answerDAO.selectByQuestionId(questionId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return answers;
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
        try {
            answerDAO.update(updatedAnswer);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        AnswerDAO answerDAO = DAOFactory.getDAOFactory().getAnswerDAO();
        try {
            answerDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
