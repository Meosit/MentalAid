package by.mksn.epam.mentalaid.service.factory;

import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.impl.AnswerServiceImpl;
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

    private ServiceFactory() {
        userService = new UserServiceImpl();
        questionService = new QuestionServiceImpl();
        answerService = new AnswerServiceImpl();
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
}
