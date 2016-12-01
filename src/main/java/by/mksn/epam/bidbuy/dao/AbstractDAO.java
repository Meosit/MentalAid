package by.mksn.epam.bidbuy.dao;

import by.mksn.epam.bidbuy.dao.exception.DaoException;
import by.mksn.epam.bidbuy.dao.pool.PooledConnection;
import by.mksn.epam.bidbuy.entity.Entity;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Basic DAO entity
 *
 * @param <E>
 */
public abstract class AbstractDAO<E extends Entity> {

    private static final Logger logger = Logger.getLogger(AbstractDAO.class);

    protected PooledConnection connection;

    protected AbstractDAO(PooledConnection connection) {
        this.connection = connection;
    }

    /**
     * Selects all entities from the database
     *
     * @return list of the selected entities
     * @throws DaoException if something went wrong
     */
    public abstract List<E> selectAll() throws DaoException;

    /**
     * Selects entity from the database with specified ID
     *
     * @param id of an entity
     * @return Entity with the specified id
     * @throws DaoException if something went wrong
     */
    public abstract E selectEntityById(long id) throws DaoException;

    /**
     * Updates entity in the database with the updated one
     * Updating uses primary key.
     *
     * @param updatedEntity entity to update
     * @throws DaoException if something went wrong
     */
    public abstract void update(E updatedEntity) throws DaoException;

    /**
     * Deletes the entity with the specified id
     *
     * @param id id of an entity to delete
     * @throws DaoException if something went wrong
     */
    public abstract void delete(long id) throws DaoException;

    /**
     * Inserts specified entity to the database
     *
     * @param entity entity to insert
     * @throws DaoException if something went wrong
     */
    public abstract void insert(E entity) throws DaoException;

}
