package by.mksn.epam.mentalaid.dao;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Entity;

/**
 * Base interface for all DAO's
 */
public interface BaseDAO<E extends Entity> {

    /**
     * Inserts new answer into database
     *
     * @param entity with some initialized properties
     * @return {@link Entity} entity with all filled properties
     * @throws DAOException if something went wrong
     */
    E insert(E entity) throws DAOException;

    /**
     * Selects entity from the database with specified ID
     *
     * @param id of an entity
     * @return - {@link Entity} with the specified id <br> - {@code null} if entity not found or deleted
     * @throws DAOException if something went wrong
     */
    E selectById(long id) throws DAOException;

    /**
     * Updates entity in the database with the updated one.
     * Updating by {@link E#id}.<br>
     *
     * @param updatedEntity entity to update
     * @throws DAOException if something went wrong
     */
    void update(E updatedEntity) throws DAOException;

    /**
     * Removes entity with the specified id
     *
     * @param id id of a question to delete
     * @throws DAOException if something went wrong
     */
    void delete(long id) throws DAOException;
}
