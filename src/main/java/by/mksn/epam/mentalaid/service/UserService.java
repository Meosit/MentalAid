package by.mksn.epam.mentalaid.service;

import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.exception.UserServiceException;

/**
 * Service which provides all needed user methods.
 */
public interface UserService extends ItemPageService<User> {

    /**
     * Returns user with specified username, or {@code null}
     * if user not found.<br>
     * <strong>Warning:</strong> user may have status {@link User#STATUS_DELETED}
     *
     * @param username username of a user to get
     * @return {@link User} entity with specified username or {@code null} if entity not found
     * @throws ServiceException if error happens during execution
     */
    User getByUsername(String username) throws ServiceException;

    /**
     * Creates new user with the specified credentials
     *
     * @param username username of user, must be unique
     * @param email    email of user, must be unique
     * @param password password of new user
     * @return {@link User} object which represents registered user
     * @throws UserServiceException if user with specified credentials is already exists (cause codes: {@link UserServiceException#EMAIL_EXISTS} or {@link UserServiceException#USER_EXISTS})<br>
     *                              - if username or email has illegal format (cause code: {@link UserServiceException#WRONG_INPUT})
     * @throws ServiceException     if error happens during execution
     */
    User register(String username, String email, String password) throws ServiceException;

    /**
     * Authorizes user with the given credentials.
     *
     * @param username username of a user
     * @param password password of a user
     * @return {@link User} entity of authorized user.
     * @throws UserServiceException if password does not match (cause code: {@link UserServiceException#INCORRECT_PASSWORD})<br>
     *                              - if such user does not exists (cause code: {@link UserServiceException#USER_NOT_EXIST})
     * @throws ServiceException     if error happens during execution
     */
    User login(String username, String password) throws ServiceException;

    /**
     * Updates user data<br>
     * <strong>Warning:</strong> there is not any validations. Use at your own risk.
     *
     * @param updatedUser updated user entity which will be stored into database
     * @throws ServiceException if error happens during execution
     */
    void updateUser(User updatedUser) throws ServiceException;

    /**
     * Replaces user password with the new one.
     *
     * @param user        {@link User} user where password will be changed.
     * @param oldPassword old (current) password of the user account (from the client)
     * @param newPassword new password for replace
     * @throws UserServiceException - if old password does not match (cause code: {@link UserServiceException#INCORRECT_PASSWORD})
     *                              <br> - if new password is null or empty (cause code: {@link UserServiceException#WRONG_INPUT})
     * @throws ServiceException     if error happens during execution
     */
    void updateUserPassword(User user, String oldPassword, String newPassword) throws ServiceException;

}
