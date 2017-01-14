package by.mksn.epam.mentalaid.service.factory;

import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.MarkService;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.impl.AnswerServiceImpl;
import by.mksn.epam.mentalaid.service.impl.MarkServiceImpl;
import by.mksn.epam.mentalaid.service.impl.QuestionServiceImpl;
import by.mksn.epam.mentalaid.service.impl.UserServiceImpl;

/**
 * Provides access to service layer objects
 */
public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();
    private UserService userService;
    private QuestionService questionService;
    private AnswerService answerService;
    private MarkService markService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
        questionService = new QuestionServiceImpl();
        answerService = new AnswerServiceImpl();
        markService = new MarkServiceImpl();
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    public AnswerService getAnswerService() {
        return answerService;
    }

    public MarkService getMarkService() {
        return markService;
    }
}
