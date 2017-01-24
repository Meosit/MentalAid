package by.mksn.epam.mentalaid.dao;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Mark;

public interface MarkDAO extends BaseDAO<Mark> {

    /**
     * Inserts mark into the database
     *
     * @param entity mark where must be set following properties:
     *               <br> - {@link Mark#userId} of new mark
     *               <br> - {@link Mark#answerId} of new mark
     *               <br> - {@link Mark#value} of new mark
     * @return {@link Mark} full entity of inserted row
     * @throws DAOException if something went wrong
     */
    @Override
    Mark insert(Mark entity) throws DAOException;

    /**
     * Selects mark from the database with specified ID
     *
     * @param id of an entity
     * @return - {@link Mark} with the specified id <br> - {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    @Override
    Mark selectById(long id) throws DAOException;

    /**
     * Selects mark for the specified answer which created by the user with specified ID from the database.
     *
     * @param userId   of an entity
     * @param answerId of an entity
     * @return - {@link Mark} with the specified {@link Mark#userId} and  <br> - {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    Mark selectByUserAndAnswerId(long userId, long answerId) throws DAOException;

    /**
     * Updates mark in the database with the updated one.
     * Updating by {@link Mark#id}.
     * This method affects only on {@link Mark#value} value.
     *
     * @param updatedEntity entity to update
     * @throws DAOException if something went wrong
     */
    @Override
    void update(Mark updatedEntity) throws DAOException;

    /**
     * Deletes the mark with the specified id
     *
     * @param id id of an entity to delete
     * @throws DAOException if something went wrong
     */
    @Override
    void delete(long id) throws DAOException;
}
