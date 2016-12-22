package by.mksn.epam.bidbuy.service;

import by.mksn.epam.bidbuy.entity.Lot;
import by.mksn.epam.bidbuy.entity.User;
import by.mksn.epam.bidbuy.service.exception.LotServiceException;
import by.mksn.epam.bidbuy.service.exception.ServiceException;

import java.util.List;

/**
 * Service which provides all needed user methods.
 */
public interface LotService {

    /**
     * Selects all lots with specified status
     *
     * @param status of an entity, can be: <br> - {@link Lot#STATUS_DELETED}
     *               <br> - {@link Lot#STATUS_CREATED}
     *               <br> - {@link Lot#STATUS_APPROVED}
     *               <br> - {@link Lot#STATUS_OPENED}
     *               <br> - {@link Lot#STATUS_CLOSED}
     * @return - List of {@link Lot} with the specified status
     * @throws ServiceException    if something went wrong
     * @throws LotServiceException if invalid status passed
     */
    List<Lot> getLotsByStatus(int status) throws ServiceException;

    /**
     * Selects all lots which belongs to {@link User} with specified ID
     *
     * @param owner - {@link User} entity
     * @return - List of {@link Lot} with the specified status
     * @throws ServiceException if something went wrong
     */
    List<Lot> getLotsByOwner(User owner) throws ServiceException;

    /**
     * Creates a new lot
     *
     * @param lot lot where must be set following properties:
     *            <br> - {@link Lot#name} of a lot (max 200 chars)
     *            <br> - {@link Lot#description} detailed description of a lot (max 1000 chars
     *            <br> - {@link Lot#ownerId} id of a {@link User} who created this lot
     *            <br> - {@link Lot#auctionType} type of auction
     *            <br> - {@link Lot#minPrice} min price of auction
     *            <br> - {@link Lot#maxPrice} max price of auction
     *            <br> - {@link Lot#bidStep} step between two nearest bids
     *            <br> - {@link Lot#durationTime} time of auction availability in seconds
     *            <br> - {@link Lot#imagePath} url or image with lot photo, can be null
     * @throws ServiceException    if something went wrong and lot was not created
     * @throws LotServiceException if one of properties above are invalid
     */
    void createLot(Lot lot) throws ServiceException;

    /**
     * Updates lot with the updated properties.
     * Updating by {@link Lot#id}.
     *
     * @param updatedLot {@link Lot} with updated properties where must be set following properties:
     *                   <br> - {@link Lot#name} of a lot (max 200 chars)
     *                   <br> - {@link Lot#description} detailed description of a lot (max 1000 chars
     *                   <br> - {@link Lot#auctionType} type of auction
     *                   <br> - {@link Lot#minPrice} min price of auction
     *                   <br> - {@link Lot#maxPrice} max price of auction
     *                   <br> - {@link Lot#bidStep} step between two nearest bids
     *                   <br> - {@link Lot#durationTime} time of auction availability in seconds
     *                   <br> - {@link Lot#imagePath} url or image with lot photo, can be null
     * @throws ServiceException    if something went wrong
     * @throws LotServiceException if one of properties above are invalid
     */
    void updateLot(Lot updatedLot) throws ServiceException;

    /**
     * Deletes the lot with the specified id
     *
     * @param id id of an entity to delete
     * @throws ServiceException if something went wrong
     */
    void deleteLot(long id) throws ServiceException;

}
