package by.mksn.epam.mentalaid.service.impl;

import by.mksn.epam.mentalaid.dao.UserDAO;
import by.mksn.epam.mentalaid.dao.factory.DAOFactory;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.exception.UserServiceException;
import by.mksn.epam.mentalaid.util.HashUtil;
import org.apache.log4j.Logger;

import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
import static by.mksn.epam.mentalaid.util.StringUtil.isNullOrEmpty;
import static by.mksn.epam.mentalaid.util.caller.DAOCaller.tryCallDAO;

public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private static final String EMAIL_REGEX = "^[a-zA-Z_0-9]+@[a-zA-Z_0-9]+\\.[a-zA-Z_0-9]+$";
    private static final String USERNAME_REGEX = "^[a-zA-Z_0-9]{3,45}$";

    private static boolean isValidEmail(String email) {
        return !isNullOrEmpty(email) && email.matches(EMAIL_REGEX);
    }

    private static boolean isValidUsername(String username) {
        return !isNullOrEmpty(username) && username.matches(USERNAME_REGEX);
    }

    @Override
    public User getByUsername(String username) throws ServiceException {
        if (isNullOrEmpty(username)) {
            return null;
        }
        UserDAO userDAO = DAOFactory.getDAOFactory().getUserDAO();
        return tryCallDAO(() -> userDAO.selectByUsername(username));
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
        UserDAO userDAO = DAOFactory.getDAOFactory().getUserDAO();
        User user;
        user = tryCallDAO(() -> userDAO.selectByUsername(username));
        if (user != null) {
            logger.debug("This username \"" + username + "\" is already exists.");
            throw new UserServiceException("User with that username is already exists", UserServiceException.USER_EXISTS);
        }

        user = tryCallDAO(() -> userDAO.selectByEmail(email));
        if (user != null) {
            logger.debug("This email \"" + email + "\" is already exists.");
            throw new UserServiceException("User with that email is already exists", UserServiceException.EMAIL_EXISTS);
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassHash(passwordHash);

        return tryCallDAO(() -> userDAO.insert(newUser));
    }

    @Override
    public User login(String username, String password) throws ServiceException {
        UserDAO userDAO = DAOFactory.getDAOFactory().getUserDAO();
        User user;
        user = tryCallDAO(() -> userDAO.selectByUsername(username));
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
        UserDAO userDAO = DAOFactory.getDAOFactory().getUserDAO();
        tryCallDAO(() -> userDAO.update(updatedUser));
    }
}
