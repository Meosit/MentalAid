package by.mksn.epam.bidbuy.entity;

import by.mksn.epam.bidbuy.entity.exception.EntityArgumentException;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.sql.Timestamp;

import static by.mksn.epam.bidbuy.util.StringUtil.isNull;
import static by.mksn.epam.bidbuy.util.StringUtil.nullableEquals;

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
     * Access only directly from the database.
     */
    public static final int STATUS_DELETED = -1;
    private static final Logger logger = Logger.getLogger(User.class);
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String passHash;
    @Getter
    private int role;
    @Getter
    @Setter
    private Timestamp createdAt;
    @Getter
    @Setter
    private Timestamp modifiedAt;
    @Getter
    private int status;
    @Getter
    @Setter
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

    /**
     * Sets role of the user
     *
     * @param role one of following items:
     *             {@link #ROLE_CLIENT};
     *             {@link #ROLE_ADMIN}
     * @throws EntityArgumentException if passed undefined role code
     */
    public void setRole(int role) {
        if (role == ROLE_ADMIN || role == ROLE_CLIENT) {
            this.role = role;
        } else {
            logger.error("Illegal role code passed. (" + role + ")");
            throw new EntityArgumentException("Illegal role code passed. (" + role + ")");
        }
    }

    /**
     * Sets status of the user
     *
     * @param status one of following items :
     *               {@link #STATUS_ACTIVE};
     *               {@link #STATUS_BANNED};
     *               {@link #STATUS_DELETED}
     * @throws EntityArgumentException if passed undefined status code
     */
    public void setStatus(int status) {
        if (status == STATUS_ACTIVE || status == STATUS_BANNED || status == STATUS_DELETED) {
            this.status = status;
        } else {
            logger.error("Illegal status code passed. (" + status + ")");
            throw new EntityArgumentException("Illegal status code passed. (" + status + ")");
        }
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
                nullableEquals(createdAt.toString(), user.getCreatedAt().toString()) &&
                nullableEquals(modifiedAt.toString(), user.getModifiedAt().toString()) &&
                nullableEquals(locale, user.getLocale());

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (isNull(email) ? 0 : email.hashCode());
        result = 17 * result + (isNull(username) ? 0 : username.hashCode());
        result = 31 * result + (isNull(passHash) ? 0 : passHash.hashCode());
        result = 17 * result + role;
        result = 31 * result + (createdAt == null ? 0 : createdAt.hashCode());
        result = 17 * result + (modifiedAt == null ? 0 : modifiedAt.hashCode());
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
