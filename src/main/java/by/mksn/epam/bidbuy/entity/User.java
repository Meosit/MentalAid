package by.mksn.epam.bidbuy.entity;

import org.apache.log4j.Logger;

import java.sql.Timestamp;

import static by.mksn.epam.bidbuy.util.NullUtil.isNull;
import static by.mksn.epam.bidbuy.util.NullUtil.nullableEquals;

/**
 * Represents table `user` in database
 */
public class User extends Entity {

    /**
     * Represents that this User entity is Client
     */
    public static final int ROLE_CLIENT = 0;
    /**
     * Represents that this User entity is Admin
     */
    public static final int ROLE_ADMIN = 1;
    /**
     * Represents that this User entity has status Active.
     * Default value
     */
    public static final int STATUS_ACTIVE = 1;
    /**
     * Represents that this User entity has status Banned.
     * It means that this user cannot bet and create lots
     */
    public static final int STATUS_BANNED = 0;
    /**
     * Represents that this User entity is deleted.
     * Setting this status equals to deleting this user from the app.
     */
    public static final int STATUS_DELETED = -1;
    private static final Logger logger = Logger.getLogger(User.class);

    private long id;
    private String email;
    private String username;
    private String passHash;
    private int role;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private int status;
    private String locale;

    public User() {
    }

    public User(long id, String email, String username,
                String passHash, int role, Timestamp createdAt,
                Timestamp modifiedAt, int status, String locale) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.passHash = passHash;
        this.role = role;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.status = status;
        this.locale = locale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id &&
                role == user.role &&
                status == user.status &&
                nullableEquals(email, user.getEmail()) &&
                nullableEquals(username, user.getUsername()) &&
                nullableEquals(passHash, user.getPassHash()) &&
                nullableEquals(createdAt, user.getCreatedAt()) &&
                nullableEquals(modifiedAt, user.getModifiedAt()) &&
                nullableEquals(locale, user.getLocale());

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (isNull(email) ? 0 : email.hashCode());
        result = 17 * result + (isNull(username) ? 0 : username.hashCode());
        result = 31 * result + (isNull(passHash) ? 0 : passHash.hashCode());
        result = 17 * result + role;
        result = 31 * result + (isNull(createdAt) ? 0 : createdAt.hashCode());
        result = 17 * result + (isNull(modifiedAt) ? 0 : modifiedAt.hashCode());
        result = 31 * result + status;
        result = 17 * result + (isNull(locale) ? 0 : locale.hashCode());
        return result;
    }

    @Override
    protected User clone() {
        return new User(id, email, username, passHash, role, createdAt, modifiedAt, status, locale);
    }

    @Override
    public String toString() {
        return "User[id=" + id +
                "; email=" + email +
                "; username=" + username +
                "; passHash=" + passHash +
                "; role=" + role +
                "; createdAt=" + createdAt +
                "; modifiedAt=" + modifiedAt +
                "; status=" + status +
                "; locale=" + locale +
                "]";
    }
}
