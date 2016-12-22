package by.mksn.epam.bidbuy.dao;

import by.mksn.epam.bidbuy.dao.exception.DAOException;
import by.mksn.epam.bidbuy.entity.Lot;
import by.mksn.epam.bidbuy.entity.User;

import java.util.List;

public interface LotDAO {

    /**
     * Inserts a new lot into database
     *
     * @param entity lot where must be set following properties:
     *               <br> - {@link Lot#name} of a lot (max 200 chars)
     *               <br> - {@link Lot#description} detailed description of a lot (max 1000 chars)
     *               <br> - {@link Lot#ownerId} id of a {@link User} who created this lot
     *               <br> - {@link Lot#auctionType} type of auction
     *               <br> - {@link Lot#minPrice} min price of auction
     *               <br> - {@link Lot#maxPrice} max price of auction
     *               <br> - {@link Lot#bidStep} step between two nearest bids
     *               <br> - {@link Lot#durationTime} time of auction availability in seconds
     *               <br> - {@link Lot#imagePath} url or image with lot photo, can be null
     * @throws DAOException if something went wrong
     */
    void insert(Lot entity) throws DAOException;

    /**
     * Selects lot from the database with specified ID
     *
     * @param id of an entity
     * @return - {@link Lot} with the specified id <br> - {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    Lot selectById(long id) throws DAOException;

    /**
     * Selects all lots from the database which belongs to {@link User} with specified ID
     *
     * @param id of owner {@link User}
     * @return - List of {@link Lot} with the specified id <br> - {@code null} if entity not found
     * @throws DAOException if something went wrong
     */
    List<Lot> selectByOwnerId(long id) throws DAOException;

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
     * Updates lot in the database with the updated one.
     * Updating by {@link Lot#id}.
     *
     * @param updatedLot lot where must be set following properties:
     *               <br> - {@link Lot#name} of a lot (max 200 chars)
     *               <br> - {@link Lot#description} detailed description of a lot (max 1000 chars)
     *               <br> - {@link Lot#auctionType} type of auction
     *               <br> - {@link Lot#minPrice} min price of auction
     *               <br> - {@link Lot#maxPrice} max price of auction
     *               <br> - {@link Lot#bidStep} step between two nearest bids
     *               <br> - {@link Lot#durationTime} time of auction availability in seconds
     *               <br> - {@link Lot#imagePath} url or image with lot photo, can be null
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

}
