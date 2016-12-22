package by.mksn.epam.bidbuy.service.impl;

import by.mksn.epam.bidbuy.dao.LotDAO;
import by.mksn.epam.bidbuy.dao.exception.DAOException;
import by.mksn.epam.bidbuy.dao.factory.DAOFactory;
import by.mksn.epam.bidbuy.entity.Lot;
import by.mksn.epam.bidbuy.entity.User;
import by.mksn.epam.bidbuy.service.LotService;
import by.mksn.epam.bidbuy.service.exception.LotServiceException;
import by.mksn.epam.bidbuy.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

import static by.mksn.epam.bidbuy.util.NullUtil.isNull;
import static by.mksn.epam.bidbuy.util.NullUtil.isNullOrEmpty;

public class LotServiceImpl implements LotService {

    private static final Logger logger = Logger.getLogger(LotServiceImpl.class);
    private static final int MAX_DESCRIPTION_LENGTH = 1000;
    private static final int MAX_NAME_LENGTH = 200;
    private static final long MAX_DURATION_TIME = 2_592_000; // 30 days
    private static final long MIN_DURATION_TIME = 300; // 5 minutes

    private static void validateLotProperties(Lot lot) throws LotServiceException {
        if (!isValidStatus(lot.getStatus())) {
            logger.warn("Invalid lot status passed (" + lot.getStatus() + ")");
            throw new LotServiceException("Invalid status", LotServiceException.INVALID_STATUS);
        }
        if (!isValidType(lot.getAuctionType())) {
            logger.warn("Invalid auction type passed (" + lot.getAuctionType() + ")");
            throw new LotServiceException("Invalid auction type", LotServiceException.INVALID_TYPE);
        }
        if (!isValidName(lot.getName())) {
            logger.warn("Invalid lot name passed (" + lot.getName() + ")");
            throw new LotServiceException("Invalid lot name", LotServiceException.INVALID_NAME);
        }
        if (!isValidDescription(lot.getDescription())) {
            logger.warn("Invalid lot description passed (" + lot.getDescription() + ")");
            throw new LotServiceException("Invalid lot description", LotServiceException.INVALID_DESCRIPTION);
        }
        if (!isValidDurationTime(lot.getDurationTime())) {
            logger.warn("Invalid duration type passed (" + lot.getDurationTime() + ")");
            throw new LotServiceException("Invalid duration time", LotServiceException.INVALID_DURATION_TIME);
        }
        if (!isValidPrice(lot.getMinPrice())) {
            logger.warn("Invalid min price passed (" + lot.getMinPrice() + ")");
            throw new LotServiceException("Invalid min price", LotServiceException.INVALID_PRICE);
        }
        if (!isValidPrice(lot.getMaxPrice())) {
            logger.warn("Invalid max price passed (" + lot.getMaxPrice() + ")");
            throw new LotServiceException("Invalid max price", LotServiceException.INVALID_PRICE);
        }
        if (lot.getMinPrice().compareTo(lot.getMaxPrice()) == 1) {
            logger.warn("Min price is bigger than max (min: " + lot.getMinPrice() + ", max: " + lot.getMaxPrice() + ")");
            throw new LotServiceException("Min price is bigger than max", LotServiceException.MIN_PRICE_BIGGER);
        }
        if (!isValidBidStep(lot.getBidStep(), lot.getMinPrice(), lot.getMaxPrice())) {
            logger.warn("Invalid bid step passed (" + lot.getBidStep() + ")");
            throw new LotServiceException("Invalid bid step passed", LotServiceException.BID_STEP_OUT_OF_RANGE);
        }
    }

    private static boolean isValidStatus(int status) {
        return status == Lot.STATUS_DELETED
                || status == Lot.STATUS_CREATED
                || status == Lot.STATUS_APPROVED
                || status == Lot.STATUS_OPENED
                || status == Lot.STATUS_CLOSED;
    }

    private static boolean isValidType(int type) {
        return type == Lot.TYPE_ENGLISH
                || type == Lot.TYPE_REVERSIVE
                || type == Lot.TYPE_LOTTERY;
    }

    private static boolean isValidName(String name) {
        return !isNullOrEmpty(name)
                && name.length() <= MAX_NAME_LENGTH;
    }

    private static boolean isValidDescription(String name) {
        return !isNullOrEmpty(name)
                && name.length() <= MAX_DESCRIPTION_LENGTH;
    }

    private static boolean isValidPrice(BigDecimal price) {
        return !isNull(price)
                && price.signum() != -1;
    }

    private static boolean isValidBidStep(BigDecimal bidStep, BigDecimal minPrice, BigDecimal maxPrice) {
        return !isNull(bidStep) && !isNull(minPrice) && !isNull(maxPrice)
                && (bidStep.compareTo(maxPrice.subtract(maxPrice)) != 1);
    }

    private static boolean isValidDurationTime(long time) {
        return MIN_DURATION_TIME <= time && time <= MAX_DURATION_TIME;
    }

    @Override
    public List<Lot> getLotsByStatus(int status) throws ServiceException {
        if (!isValidStatus(status)) {
            logger.warn("Invalid status passed (" + status + ")");
            throw new LotServiceException("Invalid status", LotServiceException.INVALID_STATUS);
        }
        LotDAO lotDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getLotDAO();
        List<Lot> lots;
        try {
            lots = lotDAO.selectByStatus(status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return lots;
    }

    @Override
    public List<Lot> getLotsByOwner(User owner) throws ServiceException {
        LotDAO lotDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getLotDAO();
        List<Lot> lots;
        try {
            lots = lotDAO.selectByOwnerId(owner.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return lots;
    }

    @Override
    public void createLot(Lot lot) throws ServiceException {
        validateLotProperties(lot);
        LotDAO lotDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getLotDAO();
        try {
            lotDAO.insert(lot);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateLot(Lot updatedLot) throws ServiceException {
        validateLotProperties(updatedLot);
        LotDAO lotDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getLotDAO();
        try {
            lotDAO.update(updatedLot);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteLot(long id) throws ServiceException {
        LotDAO lotDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getLotDAO();
    }
}
