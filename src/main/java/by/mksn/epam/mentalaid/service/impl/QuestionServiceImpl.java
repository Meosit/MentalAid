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

    private QuestionDAO getQuestionDAO() {
        return DAOFactory.getDAOFactory().getQuestionDAO();
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


        return tryCallDAO(() -> getQuestionDAO().insert(question));
    }

    @Override
    public Question getById(long id) throws ServiceException {
        return tryCallDAO(() -> getQuestionDAO().selectById(id));
    }

    @Override
    public int getUserQuestionCount(String username) throws ServiceException {
        return tryCallDAO(() -> getQuestionDAO().selectCountByUsername(username));
    }

    @Override
    public ItemsPage<Question> getPageForUser(String username, int index) throws ServiceException {
        if ((index - 1) * ITEMS_PER_PAGE < 0) {
            index = 1;
        }
        int offset = (index - 1) * ITEMS_PER_PAGE;
        List<Question> items = tryCallDAO(() -> getQuestionDAO().selectByUsernameWithLimit(username, offset, ITEMS_PER_PAGE));
        int questionCount = tryCallDAO(() -> getQuestionDAO().selectCountByUsername(username));
        int pageCount = calculatePageCount(questionCount, ITEMS_PER_PAGE);
        return new ItemsPage<>(items, pageCount, index);
    }

    @Override
    public ItemsPage<Question> getSearchPageForUser(String username, int index, String searchQuery) throws ServiceException {
        if ((index - 1) * ITEMS_PER_PAGE < 0) {
            index = 1;
        }
        int offset = (index - 1) * ITEMS_PER_PAGE;
        List<Question> items = tryCallDAO(() ->
                getQuestionDAO().selectLikeByUsernameWithLimit(searchQuery, username, offset, ITEMS_PER_PAGE));
        int questionCount = tryCallDAO(() -> getQuestionDAO().selectLikeCountByUsername(searchQuery, username));
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

        tryCallDAO(() -> getQuestionDAO().update(updatedQuestion));
    }

    @Override
    public void delete(long id) throws ServiceException {
        DAOCaller.tryCallDAO(() -> getQuestionDAO().delete(id));
    }

    @Override
    public ItemsPage<Question> getPage(int index, int itemsPerPage) throws ServiceException {
        if ((index - 1) * itemsPerPage < 0) {
            index = 1;
        }
        int offset = (index - 1) * itemsPerPage;

        List<Question> items = tryCallDAO(() -> getQuestionDAO().selectWithLimit(offset, itemsPerPage));
        int questionCount = tryCallDAO(getQuestionDAO()::selectCount);
        int pageCount = calculatePageCount(questionCount, itemsPerPage);
        return new ItemsPage<>(items, pageCount, index);
    }

    @Override
    public ItemsPage<Question> getSearchPage(int index, int itemsPerPage, String searchQuery) throws ServiceException {
        if ((index - 1) * itemsPerPage < 0) {
            index = 1;
        }
        int offset = (index - 1) * itemsPerPage;

        List<Question> items = tryCallDAO(() -> getQuestionDAO().selectLikeWithLimit(searchQuery, offset, itemsPerPage));
        int questionCount = tryCallDAO(() -> getQuestionDAO().selectLikeCount(searchQuery));
        int pageCount = calculatePageCount(questionCount, itemsPerPage);
        return new ItemsPage<>(items, pageCount, index);
    }


}
