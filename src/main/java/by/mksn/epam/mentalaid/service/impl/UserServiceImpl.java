package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.UserDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.exception.UserServiceException;
import by.mksn.epam.mentalaid.util.HashUtil;
import org.apache.log4j.Logger;

import java.util.List;

import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
import static by.mksn.epam.mentalaid.util.StringUtil.isNullOrEmpty;
import static by.mksn.epam.mentalaid.util.caller.DAOCaller.tryCallDAO;

public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private static final String EMAIL_REGEX = "^[a-zA-Z_0-9]+@[a-zA-Z_0-9]+\\.[a-zA-Z_0-9]+$";
    private static final String USERNAME_REGEX = "^[a-zA-Z_0-9]{3,45}$";

    private static int calculatePageCount(int questionCount, int itemCount) {
        int pageCount;
        if ((questionCount % itemCount == 0) && (questionCount != 0)) {
            pageCount = questionCount / itemCount;
        } else {
            pageCount = questionCount / itemCount + 1;
        }
        return pageCount;
    }

    private static boolean isValidEmail(String email) {
        return !isNullOrEmpty(email) && email.matches(EMAIL_REGEX);
    }

    private static boolean isValidUsername(String username) {
        return !isNullOrEmpty(username) && username.matches(USERNAME_REGEX);
    }

    private UserDAO getUserDAO() {
        return DAOFactory.getDAOFactory().getUserDAO();
    }

    @Override
    public User getByUsername(String username) throws ServiceException {
        if (isNullOrEmpty(username)) {
            return null;
        }
        return tryCallDAO(() -> getUserDAO().selectByUsername(username));
    }

    @Override
    public User register(String username, String email, String password) throws ServiceException {
        if (!isValidEmail(email)) {
            logger.debug("Invalid email format of \"" + email + "\"");
            throw new UserServiceException("Invalid email format", UserServiceException.WRONG_INPUT);
        }
        if (!isValidUsername(username)) {
            logger.debug("Invalid username format of \"" + username + "\"");
            throw new UserServiceException("Invalid username format", UserServiceException.WRONG_INPUT);
        }

        String passwordHash = HashUtil.hashString(password);
        User user;
        user = tryCallDAO(() -> getUserDAO().selectByUsername(username));
        if (!isNull(user)) {
            logger.debug("This username \"" + username + "\" is already exists.");
            throw new UserServiceException("User with that username is already exists", UserServiceException.USER_EXISTS);
        }

        user = tryCallDAO(() -> getUserDAO().selectByEmail(email));
        if (!isNull(user)) {
            logger.debug("This email \"" + email + "\" is already exists.");
            throw new UserServiceException("User with that email is already exists", UserServiceException.EMAIL_EXISTS);
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassHash(passwordHash);

        return tryCallDAO(() -> getUserDAO().insert(newUser));
    }

    @Override
    public User login(String username, String password) throws ServiceException {
        User user;
        user = tryCallDAO(() -> getUserDAO().selectByUsername(username));
        if (!isNull(user) && (user.getStatus() != User.STATUS_DELETED)) {
            if (!HashUtil.isValidHash(password, user.getPassHash())) {
                logger.debug("Incorrect password for username \"" + username + "\"");
                throw new UserServiceException("Incorrect password.", UserServiceException.INCORRECT_PASSWORD);
            }
        } else {
            logger.debug("User with username \"" + username + "\" doesn't exists.");
            throw new UserServiceException("Incorrect password.", UserServiceException.USER_NOT_EXIST);
        }
        return user;
    }

    @Override
    public void updateUser(User updatedUser) throws ServiceException {
        tryCallDAO(() -> getUserDAO().update(updatedUser));
    }

    @Override
    public void updateUserPassword(User user, String oldPassword, String newPassword) throws ServiceException {
        if (isNullOrEmpty(newPassword)) {
            logger.debug("Null or empty new password for user \"" + user.getUsername() + "\"");
            throw new UserServiceException("Null or empty new password", UserServiceException.WRONG_INPUT);
        }

        if (HashUtil.isValidHash(oldPassword, user.getPassHash())) {
            user.setPassHash(HashUtil.hashString(newPassword));
            tryCallDAO(() -> getUserDAO().update(user));
        } else {
            logger.debug("Incorrect old password for user \"" + user.getUsername() + "\"");
            throw new UserServiceException(UserServiceException.INCORRECT_PASSWORD);
        }
    }

    @Override
    public ItemsPage<User> getPage(int index, int itemsPerPage) throws ServiceException {
        if ((index - 1) * itemsPerPage < 0) {
            index = 1;
        }
        int offset = (index - 1) * itemsPerPage;

        List<User> items = tryCallDAO(() -> getUserDAO().selectWithLimit(offset, itemsPerPage));
        int questionCount = tryCallDAO(getUserDAO()::selectCount);
        int pageCount = calculatePageCount(questionCount, itemsPerPage);
        return new ItemsPage<>(items, pageCount, index);
    }

    @Override
    public ItemsPage<User> getSearchPage(int index, int itemsPerPage, String searchQuery) throws ServiceException {
        if ((index - 1) * itemsPerPage < 0) {
            index = 1;
        }
        int offset = (index - 1) * itemsPerPage;

        List<User> items = tryCallDAO(() -> getUserDAO().selectLikeWithLimit(searchQuery, offset, itemsPerPage));
        int questionCount = tryCallDAO(() -> getUserDAO().selectLikeCount(searchQuery));
        int pageCount = calculatePageCount(questionCount, itemsPerPage);
        return new ItemsPage<>(items, pageCount, index);
    }
}
