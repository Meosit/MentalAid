package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.QuestionDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.QuestionServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

import static by.mksn.epam.mentalaid.service.impl.DAOCaller.tryCallDAO;
import static by.mksn.epam.mentalaid.util.StringUtil.*;

public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = Logger.getLogger(QuestionServiceImpl.class);
    private static final int TITLE_MAX_LENGTH = 200;
    private static final int DESCRIPTION_MAX_LENGTH = 2000;

    private static int calculatePageCount(int questionCount, int questionsPerPage) {
        int pageCount;
        if ((questionCount % questionsPerPage == 0) && (questionCount != 0)) {
            pageCount = questionCount / questionsPerPage;
        } else {
            pageCount = questionCount / questionsPerPage + 1;
        }
        return pageCount;
    }

    @Override
    public Question add(Question question) throws ServiceException {
        if (isNullOrEmpty(question.getTitle())) {
            throw new QuestionServiceException("Null or empty title passed", QuestionServiceException.WRONG_INPUT);
        }
        if (isNullOrEmpty(question.getDescription())) {
            throw new QuestionServiceException("Null or empty description passed", QuestionServiceException.WRONG_INPUT);
        }

        String normalizedTitle = trimAndCollapseNewLines(question.getTitle());
        normalizedTitle = truncateToSize(normalizedTitle, TITLE_MAX_LENGTH);
        question.setTitle(normalizedTitle);

        String normalizedDescription = trimAndCollapseNewLines(question.getDescription());
        normalizedDescription = truncateToSize(normalizedDescription, DESCRIPTION_MAX_LENGTH);
        question.setDescription(normalizedDescription);


        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        return tryCallDAO(() -> questionDAO.insert(question));
    }

    @Override
    public Question getById(long id) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        return tryCallDAO(() -> questionDAO.selectById(id));
    }

    @Override
    public List<Question> getQuestionsPage(int index, int questionsPerPage) throws ServiceException {
        int offset = (index - 1) * questionsPerPage;
        if (offset < 0) {
            logger.debug("Invalid page index passed (" + index + ")");
            throw new QuestionServiceException("Invalid index passed", QuestionServiceException.INVALID_PAGE_INDEX);
        }

        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        return tryCallDAO(() -> questionDAO.selectWithLimit(offset, questionsPerPage));
    }

    @Override
    public List<Question> getSearchQuestionsPage(String searchQuery, int index, int questionsPerPage) throws ServiceException {
        int offset = (index - 1) * questionsPerPage;
        if (offset < 0) {
            logger.debug("Invalid page index passed (" + index + ")");
            throw new QuestionServiceException("Invalid index passed", QuestionServiceException.INVALID_PAGE_INDEX);
        }

        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        return tryCallDAO(() -> questionDAO.selectLikeWithLimit(searchQuery, offset, questionsPerPage));
    }

    @Override
    public List<Question> getPageForUserQuestions(String username, int index, int questionsPerPage) throws ServiceException {
        int offset = (index - 1) * questionsPerPage;
        if (offset < 0) {
            logger.debug("Invalid page index passed (" + index + ")");
            throw new QuestionServiceException("Invalid index passed", QuestionServiceException.INVALID_PAGE_INDEX);
        }

        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        return tryCallDAO(() -> questionDAO.selectByUsernameWithLimit(username, offset, questionsPerPage));
    }

    @Override
    public List<Question> getSearchPageForUserQuestions(String searchQuery, String username, int index, int questionsPerPage) throws ServiceException {
        int offset = (index - 1) * questionsPerPage;
        if (offset < 0) {
            logger.debug("Invalid page index passed (" + index + ")");
            throw new QuestionServiceException("Invalid index passed", QuestionServiceException.INVALID_PAGE_INDEX);
        }

        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        return tryCallDAO(() -> questionDAO.selectLikeByUsernameWithLimit(searchQuery, username, offset, questionsPerPage));
    }

    @Override
    public int getPageCount(int questionsPerPage) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        int questionCount = tryCallDAO(questionDAO::selectCount);
        return calculatePageCount(questionCount, questionsPerPage);
    }

    @Override
    public int getSearchPageCount(String searchQuery, int questionsPerPage) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        int questionCount = tryCallDAO(() -> questionDAO.selectLikeCount(searchQuery));
        return calculatePageCount(questionCount, questionsPerPage);
    }

    @Override
    public int getPageCountForUserQuestions(String username, int questionsPerPage) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        int questionCount = tryCallDAO(() -> questionDAO.selectCountByUsername(username));
        return calculatePageCount(questionCount, questionsPerPage);
    }

    @Override
    public int getSearchPageCountForUserQuestions(String searchQuery, String username, int questionsPerPage) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        int questionCount = tryCallDAO(() -> questionDAO.selectLikeCountByUsername(searchQuery, username));
        return calculatePageCount(questionCount, questionsPerPage);
    }

    @Override
    public void update(Question updatedQuestion) throws ServiceException {
        if (isNullOrEmpty(updatedQuestion.getTitle())) {
            throw new QuestionServiceException("Null or empty title passed", QuestionServiceException.WRONG_INPUT);
        }
        if (isNullOrEmpty(updatedQuestion.getDescription())) {
            throw new QuestionServiceException("Null or empty description passed", QuestionServiceException.WRONG_INPUT);
        }

        String normalizedTitle = trimAndCollapseNewLines(updatedQuestion.getTitle());
        normalizedTitle = truncateToSize(normalizedTitle, TITLE_MAX_LENGTH);
        updatedQuestion.setTitle(normalizedTitle);

        String normalizedDescription = trimAndCollapseNewLines(updatedQuestion.getDescription());
        normalizedDescription = truncateToSize(normalizedDescription, DESCRIPTION_MAX_LENGTH);
        updatedQuestion.setDescription(normalizedDescription);

        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        tryCallDAO(() -> questionDAO.update(updatedQuestion));
    }

    @Override
    public void delete(long id) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        DAOCaller.tryCallDAO(() -> questionDAO.delete(id));
    }
}
