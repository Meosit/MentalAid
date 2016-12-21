package by.mksn.epam.bidbuy.dao;

import by.mksn.epam.bidbuy.dao.exception.DAOException;
import by.mksn.epam.bidbuy.entity.Lot;
import by.mksn.epam.bidbuy.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface LotDAO {

    /**
     * Selects lot from the database with specified ID
     *
     * @param id of an entity
     * @return - {@link Lot} with the specified id <br> - {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    Lot selectById(long id) throws DAOException;

    /**
     * Selects all lots from the database with specified status
     *
     * @param status of an entity, can be: <br> - {@link Lot#STATUS_DELETED}
     *               <br> - {@link Lot#STATUS_CREATED}
     *               <br> - {@link Lot#STATUS_APPROVED}
     *               <br> - {@link Lot#STATUS_OPENED}
     *               <br> - {@link Lot#STATUS_CLOSED}
     * @return - List of {@link Lot} with the specified status
     * @throws DAOException if something went wrong
     */
    List<Lot> selectByStatus(int status) throws DAOException;

    /**
     * Selects all lots from the database with specified type
     *
     * @param type of an auction, can be: <br> - {@link Lot#TYPE_ENGLISH}
     *             <br> - {@link Lot#TYPE_REVERSIVE}
     *             <br> - {@link Lot#TYPE_LOTTERY}
     * @return - List of {@link Lot} with the specified status
     * @throws DAOException if something went wrong
     */
    List<Lot> selectByType(int type) throws DAOException;

    /**
     * Updates lot in the database with the updated one.
     * Updating by {@link Lot#id}.
     *
     * @param updatedLot entity to update
     * @throws DAOException if something went wrong
     */
    void update(Lot updatedLot) throws DAOException;

    /**
     * Deletes the lot with the specified id
     *
     * @param id id of an entity to delete
     * @throws DAOException if something went wrong
     */
    void delete(long id) throws DAOException;

    /**
     * Inserts a new lot into database
     *
     * @param name         name of a lot (max 200 chars)
     * @param description  detailed description of a lot (max 1000 chars)
     * @param ownerId      id of a {@link User} who created this lot
     * @param auctionType  type of auction, must be one
     * @param minPrice     min price of auction
     * @param maxPrice     max price of auction
     * @param bidStep      step between two nearest bids
     * @param durationTime time of auction availability in seconds
     * @param imagePath    url or image with lot photo
     * @throws DAOException if something went wrong
     */
    void insert(String name, String description, long ownerId, int auctionType, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal bidStep, int durationTime, String imagePath) throws DAOException;
}
