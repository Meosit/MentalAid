package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.QuestionDAO;
import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.QuestionServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = Logger.getLogger(QuestionServiceImpl.class);

    @Override
    public Question add(Question question) throws ServiceException {
        if (isNull(question.getTitle())) {
            throw new QuestionServiceException("Null title passed", QuestionServiceException.WRONG_INPUT);
        }
        if (isNull(question.getDescription())) {
            throw new QuestionServiceException("Null description passed", QuestionServiceException.WRONG_INPUT);
        }

        QuestionDAO questionDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getQuestionDAO();
        try {
            question = questionDAO.insert(question);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return question;
    }

    @Override
    public Question getById(long id) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getQuestionDAO();
        Question question;
        try {
            question = questionDAO.selectById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return question;
    }

    @Override
    public List<Question> getQuestionsPage(int index, int questionsPerPage) throws ServiceException {
        int offset = (index - 1) * questionsPerPage;
        if (offset < 0) {
            logger.debug("Invalid page index passed (" + index + ")");
            throw new QuestionServiceException("Invalid index passed", QuestionServiceException.INVALID_PAGE_INDEX);
        }

        QuestionDAO questionDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getQuestionDAO();
        List<Question> questions;
        try {
            questions = questionDAO.selectWithLimit(offset, questionsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return questions;
    }

    @Override
    public List<Question> getQuestionsPage(String username, int index, int questionsPerPage) throws ServiceException {
        int offset = (index - 1) * questionsPerPage;
        if (offset < 0) {
            logger.debug("Invalid page index passed (" + index + ")");
            throw new QuestionServiceException("Invalid index passed", QuestionServiceException.INVALID_PAGE_INDEX);
        }

        QuestionDAO questionDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getQuestionDAO();
        List<Question> questions;
        try {
            questions = questionDAO.selectByUsernameWithLimit(username, offset, questionsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return questions;
    }

    @Override
    public int getPageCount(int questionsPerPage) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getQuestionDAO();
        int pageCount;
        try {
            int questionCount = questionDAO.selectCount();
            if ((questionCount % questionsPerPage == 0) && (questionCount != 0)) {
                pageCount = questionCount / questionsPerPage;
            } else {
                pageCount = questionCount / questionsPerPage + 1;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return pageCount;
    }

    @Override
    public int getPageCount(String username, int questionsPerPage) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getQuestionDAO();
        int pageCount;
        try {
            int questionCount = questionDAO.selectCountByUsername(username);
            if ((questionCount % questionsPerPage == 0) && (questionCount != 0)) {
                pageCount = questionCount / questionsPerPage;
            } else {
                pageCount = questionCount / questionsPerPage + 1;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return pageCount;
    }

    @Override
    public void update(Question updatedQuestion) throws ServiceException {
        if (isNull(updatedQuestion.getTitle())) {
            throw new QuestionServiceException("Null title passed", QuestionServiceException.WRONG_INPUT);
        }
        if (isNull(updatedQuestion.getDescription())) {
            throw new QuestionServiceException("Null description passed", QuestionServiceException.WRONG_INPUT);
        }

        QuestionDAO questionDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getQuestionDAO();
        try {
            questionDAO.update(updatedQuestion);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getQuestionDAO();
        try {
            questionDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
