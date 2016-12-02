package by.mksn.epam.bidbuy.entity;

import lombok.Getter;
import lombok.Setter;

import static by.mksn.epam.bidbuy.util.StringUtil.isNull;
import static by.mksn.epam.bidbuy.util.StringUtil.nullableEquals;

/**
 * Represents table `shipment_address` in database
 */
public final class ShipmentAddress extends Entity {

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private long userId;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
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
    protected Object clone() throws CloneNotSupportedException {
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
