package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.QuestionDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.QuestionServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.util.caller.DAOCaller;
import org.apache.log4j.Logger;

import java.util.List;

import static by.mksn.epam.mentalaid.util.StringUtil.*;
import static by.mksn.epam.mentalaid.util.caller.DAOCaller.tryCallDAO;

public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = Logger.getLogger(QuestionServiceImpl.class);
    private static final int TITLE_MAX_LENGTH = 200;
    private static final int DESCRIPTION_MAX_LENGTH = 2000;

    private static int calculatePageCount(int questionCount, int itemCount) {
        int pageCount;
        if ((questionCount % itemCount == 0) && (questionCount != 0)) {
            pageCount = questionCount / itemCount;
        } else {
            pageCount = questionCount / itemCount + 1;
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
    public int getUserQuestionCount(String username) throws ServiceException {
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        return tryCallDAO(() -> questionDAO.selectCountByUsername(username));
    }

    @Override
    public ItemsPage<Question> getPageForUser(String username, int index) throws ServiceException {
        if ((index - 1) * ITEMS_PER_PAGE < 0) {
            index = 1;
        }
        int offset = (index - 1) * ITEMS_PER_PAGE;
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        List<Question> items = tryCallDAO(() -> questionDAO.selectByUsernameWithLimit(username, offset, ITEMS_PER_PAGE));
        int questionCount = tryCallDAO(() -> questionDAO.selectCountByUsername(username));
        int pageCount = calculatePageCount(questionCount, ITEMS_PER_PAGE);
        return new ItemsPage<>(items, pageCount, index);
    }

    @Override
    public ItemsPage<Question> getSearchPageForUser(String username, int index, String searchQuery) throws ServiceException {
        if ((index - 1) * ITEMS_PER_PAGE < 0) {
            index = 1;
        }
        int offset = (index - 1) * ITEMS_PER_PAGE;
        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        List<Question> items = tryCallDAO(() ->
                questionDAO.selectLikeByUsernameWithLimit(searchQuery, username, offset, ITEMS_PER_PAGE));
        int questionCount = tryCallDAO(() -> questionDAO.selectLikeCountByUsername(searchQuery, username));
        int pageCount = calculatePageCount(questionCount, ITEMS_PER_PAGE);
        return new ItemsPage<>(items, pageCount, index);
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

    @Override
    public ItemsPage<Question> getPage(int index, int itemsPerPage) throws ServiceException {
        if ((index - 1) * itemsPerPage < 0) {
            index = 1;
        }
        int offset = (index - 1) * itemsPerPage;

        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        List<Question> items = tryCallDAO(() -> questionDAO.selectWithLimit(offset, itemsPerPage));
        int questionCount = tryCallDAO(questionDAO::selectCount);
        int pageCount = calculatePageCount(questionCount, itemsPerPage);
        return new ItemsPage<>(items, pageCount, index);
    }

    @Override
    public ItemsPage<Question> getSearchPage(int index, int itemsPerPage, String searchQuery) throws ServiceException {
        if ((index - 1) * itemsPerPage < 0) {
            index = 1;
        }
        int offset = (index - 1) * itemsPerPage;

        QuestionDAO questionDAO = DAOFactory.getDAOFactory().getQuestionDAO();
        List<Question> items = tryCallDAO(() -> questionDAO.selectLikeWithLimit(searchQuery, offset, itemsPerPage));
        int questionCount = tryCallDAO(() -> questionDAO.selectLikeCount(searchQuery));
        int pageCount = calculatePageCount(questionCount, itemsPerPage);
        return new ItemsPage<>(items, pageCount, index);
    }


}
