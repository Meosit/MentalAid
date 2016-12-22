package by.mksn.epam.bidbuy.entity;

import static by.mksn.epam.bidbuy.util.NullUtil.isNull;
import static by.mksn.epam.bidbuy.util.NullUtil.nullableEquals;

/**
 * Represents table `shipment_address` in database
 */
public final class ShipmentAddress extends Entity {

    private long id;
    private long userId;
    private String address;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public ShipmentAddress() {
    }

    public ShipmentAddress(long id, long userId, String address, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentAddress that = (ShipmentAddress) o;

        return id == that.id &&
                userId == that.userId &&
                nullableEquals(address, that.getAddress()) &&
                nullableEquals(firstName, that.getFirstName()) &&
                nullableEquals(lastName, that.getLastName()) &&
                nullableEquals(phoneNumber, that.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 17 * result + (isNull(address) ? 0 : address.hashCode());
        result = 31 * result + (isNull(firstName) ? 0 : firstName.hashCode());
        result = 17 * result + (isNull(lastName) ? 0 : lastName.hashCode());
        result = 31 * result + (isNull(phoneNumber) ? 0 : phoneNumber.hashCode());
        return result;
    }

    @Override
    protected ShipmentAddress clone() {
        return new ShipmentAddress(id, userId, address, firstName, lastName, phoneNumber);
    }

    @Override
    public String toString() {
        return "ShipmentAddress[id=" + id +
                "; userId=" + userId +
                "; address=" + address +
                "; firstName=" + firstName +
                "; lastName=" + lastName +
                "; phoneNumber=" + phoneNumber +
                "]";
    }
}
