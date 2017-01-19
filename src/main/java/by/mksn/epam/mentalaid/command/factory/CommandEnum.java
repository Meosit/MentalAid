package by.mksn.epam.mentalaid.command.factory;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.impl.*;
import by.mksn.epam.mentalaid.command.impl.async.*;

/**
 * Represents full list of commands, used in factory
 */
public enum CommandEnum {

    /**
     * This command provides site entry point page
     */
    INDEX(new GetIndexPageCommand()),
    /**
     * This command provides page with registration form
     */
    REGISTER(new GetRegisterPageCommand()),
    /**
     * This command provides page with login form
     */
    LOGIN(new GetLoginPageCommand()),
    /**
     * This command provides home page with question list
     */
    HOME(new GetHomePageCommand()),
    /**
     * This command provides user profile page
     */
    PROFILE(new GetProfilePageCommand()),
    /**
     * This command provides question page
     */
    QUESTION(new GetQuestionPageCommand()),
    /**
     * This command provides new question page
     */
    NEW_QUESTION(new GetNewQuestionPageCommand()),
    /**
     * This command add query attribute and redirect to the appropriate command to show filtered question list
     */
    SEARCH(new SearchRedirectCommand()),
    /**
     * Changes site content locale and saves it in database if user is signed in
     */
    SET_LOCALE(new SetLocaleCommand()),
    /**
     * Logs out user from the site
     */
    LOGOUT(new LogoutCommand()),
    /**
     * Authorizes user on site
     */
    ASYNC_LOGIN(new LoginCommand()),
    /**
     * Registers new user on site
     */
    ASYNC_REGISTER(new RegisterCommand()),
    /**
     * Changes question data
     */
    ASYNC_QUESTION_EDIT(new EditQuestionCommand()),
    /**
     * Deletes question
     */
    ASYNC_QUESTION_DELETE(new DeleteQuestionCommand()),
    /**
     * Creates new question
     */
    ASYNC_QUESTION_ADD(new AddQuestionCommand()),
    /**
     * Changes answer data
     */
    ASYNC_ANSWER_EDIT(new EditAnswerCommand()),
    /**
     * Deletes answer
     */
    ASYNC_ANSWER_DELETE(new DeleteAnswerCommand()),
    /**
     * Creates new answer
     */
    ASYNC_ANSWER_ADD(new AddAnswerCommand()),
    /**
     * Creates new mark to a question
     */
    ASYNC_MARK_ADD(new AddMarkCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
