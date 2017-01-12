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
     * This command provides questions list page
     */
    QUESTIONS(new GetQuestionListPageCommand()),
    /**
     * This command provides question page
     */
    QUESTION(new GetQuestionPageCommand()),
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
     * Changes answer data
     */
    ASYNC_ANSWER_EDIT(new EditAnswerCommand()),
    /**
     * Creates new answer
     */
    ASYNC_ANSWER_ADD(new AddAnswerCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
