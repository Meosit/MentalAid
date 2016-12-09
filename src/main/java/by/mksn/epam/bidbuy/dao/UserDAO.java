package by.mksn.epam.bidbuy.dao;

import by.mksn.epam.bidbuy.dao.exception.DAOException;
import by.mksn.epam.bidbuy.entity.User;

public interface UserDAO {
    /**
     * Selects user from the database with specified ID
     *
     * @param id of an entity
     * @return {@link User) with the specified id, {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    User selectById(long id) throws DAOException;

    /**
     * Updates user in the database with the updated one
     * Updating uses primary key.
     *
     * @param updatedUser entity to update
     * @throws DAOException if something went wrong
     */
    void update(User updatedUser) throws DAOException;

    /**
     * Deletes the user with the specified id
     *
     * @param id id of an entity to delete
     * @throws DAOException if something went wrong
     */
    void delete(long id) throws DAOException;

    /**
     * Inserts user to the database
     *
     * @param user user to insert
     * @throws DAOException if something went wrong
     */
    void insert(User user) throws DAOException;

}
