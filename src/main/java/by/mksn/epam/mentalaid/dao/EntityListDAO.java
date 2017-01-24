package by.mksn.epam.mentalaid.dao;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Entity;

import java.util.List;

/**
 * Interface which provides base methods to wirk with lists of entities
 *
 * @param <E>
 */
public interface EntityListDAO<E extends Entity> {

    /**
     * Selects list of entities with limit constraints.<br>
     * Result list sorted by create date descending.<br>
     *
     * @param offset offset of the first entity in selected list (0 means from the beginning)
     * @param count  count of entities to select (result list size may be less than specified count)
     * @return list of {@link Entity} with the specified limit
     * @throws DAOException if something went wrong
     */
    List<E> selectWithLimit(int offset, int count) throws DAOException;

    /**
     * Selects list of entities with LIKE and LIMIT constraints.<br>
     *
     * @param likeQuery query for like select
     * @param offset    offset of the first entity in selected list (0 means from the beginning)
     * @param count     count of entity to select (result list size may be less than specified count)
     * @return list of {@link Entity} with the specified limit
     * @throws DAOException if something went wrong
     */
    List<E> selectLikeWithLimit(String likeQuery, int offset, int count) throws DAOException;

    /**
     * Selects count of all entities
     *
     * @return count of all entities
     * @throws DAOException if something went wrong
     */
    int selectCount() throws DAOException;

    /**
     * Selects count of all entities with LIKE constraints
     *
     * @param likeQuery query for like select
     * @return count of all entities
     * @throws DAOException if something went wrong
     */
    int selectLikeCount(String likeQuery) throws DAOException;

}
