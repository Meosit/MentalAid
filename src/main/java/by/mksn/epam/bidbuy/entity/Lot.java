package by.mksn.epam.bidbuy.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static by.mksn.epam.bidbuy.util.NullUtil.isNull;
import static by.mksn.epam.bidbuy.util.NullUtil.nullableEquals;

/**
 * Represents table `lot` in database
 */
public class Lot extends Entity {

    /**
     * Represents that this Lot has english auction type.
     */
    public static final int TYPE_ENGLISH = 1;
    /**
     * Represents that this Lot has reversive auction type.
     * That means .
     */
    public static final int TYPE_REVERSIVE = 2;
    /**
     * Represents that this Lot has lottery auction type.
     */
    public static final int TYPE_LOTTERY = 3;
    /**
     * Represents that this Lot entity is deleted, and not sold.
     * Setting this status equals to deleting this lot from site.
     */
    public static final int STATUS_DELETED = -1;
    /**
     * Represents that this Lot entity just created and not approved.
     * Default value
     */
    public static final int STATUS_CREATED = 0;
    /**
     * Represents that this Lot entity is approved and ready to publishing.
     */
    public static final int STATUS_APPROVED = 1;
    /**
     * Represents that this Lot entity is published and other users can bid and buy this lot
     */
    public static final int STATUS_OPENED = 2;
    /**
     * Represents that this Lot entity is closed (sold or it's time is up)
     */
    public static final int STATUS_CLOSED = 3;

    private long id;
    private long ownerId;
    private long leaderBetId;
    private int auctionType;
    private BigDecimal minPrice;
    private BigDecimal currentPrice;
    private BigDecimal maxPrice;
    private BigDecimal bidStep;
    private long durationTime;
    private String name;
    private String description;
    private String imagePath;
    private int status;
    private Timestamp statusChangedAt;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    public Lot(long id, long ownerId, long leaderBetId, int auctionType, BigDecimal minPrice, BigDecimal currentPrice, BigDecimal maxPrice, BigDecimal bidStep, long durationTime, String name, String description, String imagePath, int status, Timestamp statusChangedAt, Timestamp createdAt, Timestamp modifiedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.leaderBetId = leaderBetId;
        this.auctionType = auctionType;
        this.minPrice = minPrice;
        this.currentPrice = currentPrice;
        this.maxPrice = maxPrice;
        this.bidStep = bidStep;
        this.durationTime = durationTime;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.status = status;
        this.statusChangedAt = statusChangedAt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Lot() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getLeaderBetId() {
        return leaderBetId;
    }

    public void setLeaderBetId(long leaderBetId) {
        this.leaderBetId = leaderBetId;
    }

    public int getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(int auctionType) {
        this.auctionType = auctionType;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getBidStep() {
        return bidStep;
    }

    public void setBidStep(BigDecimal bidStep) {
        this.bidStep = bidStep;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(Timestamp statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lot lot = (Lot) o;

        return id == lot.getId()
                && ownerId == lot.getOwnerId()
                && leaderBetId == lot.getLeaderBetId()
                && auctionType == lot.getAuctionType()
                && durationTime == lot.getDurationTime()
                && status == lot.getStatus()
                && nullableEquals(minPrice, lot.getMinPrice())
                && nullableEquals(currentPrice, lot.getCurrentPrice())
                && nullableEquals(maxPrice, lot.getMaxPrice())
                && nullableEquals(bidStep, lot.getBidStep())
                && nullableEquals(name, lot.getName())
                && nullableEquals(description, lot.getDescription())
                && nullableEquals(imagePath, lot.getImagePath())
                && nullableEquals(statusChangedAt, lot.getStatusChangedAt())
                && nullableEquals(createdAt, lot.getCreatedAt())
                && nullableEquals(modifiedAt, lot.getModifiedAt());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (getOwnerId() ^ (getOwnerId() >>> 32));
        result = 17 * result + (int) (getLeaderBetId() ^ (getLeaderBetId() >>> 32));
        result = 31 * result + auctionType;
        result = 17 * result + (!isNull(minPrice) ? minPrice.hashCode() : 0);
        result = 31 * result + (!isNull(currentPrice) ? currentPrice.hashCode() : 0);
        result = 17 * result + (!isNull(maxPrice) ? maxPrice.hashCode() : 0);
        result = 31 * result + (!isNull(bidStep) ? bidStep.hashCode() : 0);
        result = 17 * result + (int) durationTime;
        result = 31 * result + (!isNull(name) ? name.hashCode() : 0);
        result = 17 * result + (!isNull(description) ? description.hashCode() : 0);
        result = 31 * result + (!isNull(imagePath) ? imagePath.hashCode() : 0);
        result = 17 * result + status;
        result = 31 * result + (!isNull(statusChangedAt) ? statusChangedAt.hashCode() : 0);
        result = 17 * result + (!isNull(createdAt) ? createdAt.hashCode() : 0);
        result = 31 * result + (!isNull(modifiedAt) ? modifiedAt.hashCode() : 0);
        return result;
    }

    @Override
    protected Object clone() {
        return new Lot(id, ownerId, leaderBetId, auctionType, minPrice, currentPrice, maxPrice, bidStep, durationTime, name, description, imagePath, status, statusChangedAt, createdAt, modifiedAt);
    }

    @Override
    public String toString() {
        return "Lot[" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", leaderBetId=" + leaderBetId +
                ", auctionType=" + auctionType +
                ", minPrice=" + minPrice +
                ", currentPrice=" + currentPrice +
                ", maxPrice=" + maxPrice +
                ", bidStep=" + bidStep +
                ", durationTime=" + durationTime +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", status=" + status +
                ", statusChangedAt=" + statusChangedAt +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ']';
    }
}
