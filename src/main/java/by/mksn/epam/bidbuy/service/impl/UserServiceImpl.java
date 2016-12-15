package by.mksn.epam.bidbuy.service.impl;

import by.mksn.epam.bidbuy.dao.UserDAO;
import by.mksn.epam.bidbuy.dao.exception.DAOException;
import by.mksn.epam.bidbuy.dao.factory.DAOFactory;
import by.mksn.epam.bidbuy.entity.User;
import by.mksn.epam.bidbuy.service.UserService;
import by.mksn.epam.bidbuy.service.exception.ServiceException;
import by.mksn.epam.bidbuy.service.exception.ServiceUserException;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {

    public static final int HASH_ROUNDS = 12;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User register(String username, String email, String password) throws ServiceException {
        UserDAO userDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getUserDAO();
        User user;
        try {
            user = userDAO.selectByUsername(username);
            if (user != null) {
                logger.debug("Registration failed: user with username \"" + username + "\" is already exists.");
                throw new ServiceUserException("User with that username is already exists", ServiceUserException.USER_EXISTS);
            }

            user = userDAO.selectByEmail(email);
            if (user != null) {
                logger.debug("Registration failed: user with email \"" + email + "\" is already exists.");
                throw new ServiceUserException("User with that email is already exists", ServiceUserException.EMAIL_EXISTS);
            }

            password = BCrypt.hashpw(password, BCrypt.gensalt(HASH_ROUNDS));

            user = userDAO.insert(username, email, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User login(String username, String password) throws ServiceException {
        UserDAO userDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getUserDAO();
        User user;
        try {
            user = userDAO.selectByUsername(username);
            if (user != null) {
                if (!BCrypt.checkpw(password, user.getPassHash())) {
                    logger.debug("Authorization failed: user with username \"" + username + "\" .");
                    throw new ServiceUserException("Incorrect password.", ServiceUserException.INCORRECT_PASSWORD);
                }
            } else {
                logger.debug("Authorization failed: user with username \"" + username + "\" doesn't exists.");
                throw new ServiceUserException("Incorrect password.", ServiceUserException.USER_NOT_EXIST);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public void updateUser(User updatedUser) throws ServiceException {
        UserDAO userDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getUserDAO();
        try {
            userDAO.update(updatedUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
