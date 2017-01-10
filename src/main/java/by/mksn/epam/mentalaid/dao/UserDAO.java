package by.mksn.epam.mentalaid.dao;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.User;

public interface UserDAO extends BaseDAO<User> {

    /**
     * Inserts user into the database
     *
     * @param entity user where must be set following properties:
     *               <br> - {@link User#username} of new user
     *               <br> - {@link User#email} of new user
     *               <br> - {@link User#passHash} of new user
     * @return {@link User} full entity of inserted row
     * @throws DAOException if something went wrong
     */
    User insert(User entity) throws DAOException;

    /**
     * Selects user from the database with specified ID
     *
     * @param id of an entity
     * @return - {@link User} with the specified id <br> - {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    User selectById(long id) throws DAOException;

    /**
     * Selects user from the database with specified username
     *
     * @param username of an entity
     * @return - {@link User} with the specified username <br> - {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    User selectByUsername(String username) throws DAOException;

    /**
     * Selects user from the database with specified email
     *
     * @param email of an entity
     * @return - {@link User} with the specified email <br> - {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    User selectByEmail(String email) throws DAOException;

    /**
     * Updates user in the database with the updated one.
     * Updating by {@link User#id}.
     * This method affects only on {@link User#email}, {@link User#username},
     * {@link User#passHash}, {@link User#status}, {@link User#locale},
     * {@link User#imageUrl}, {@link User#website} values
     *
     * @param updatedEntity entity to update
     * @throws DAOException if something went wrong
     */
    void update(User updatedEntity) throws DAOException;

    /**
     * Deletes the user with the specified id
     *
     * @param id id of an entity to delete
     * @throws DAOException if something went wrong
     */
    void delete(long id) throws DAOException;

}
