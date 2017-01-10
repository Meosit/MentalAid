package by.mksn.epam.mentalaid.entity;

import java.sql.Timestamp;

import static by.mksn.epam.mentalaid.util.NullUtil.nullableEquals;
import static by.mksn.epam.mentalaid.util.NullUtil.nullableHashCode;

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

    private String email;
    private String username;
    private String passHash;
    private int role;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private int status;
    private String locale;
    private String imageUrl;
    private String website;
    /**
     * Selected from `mark` page
     */
    private float averageMark;

    public User() {
    }

    public User(long id, String email, String username,
                String passHash, int role, Timestamp createdAt,
                Timestamp modifiedAt, int status, String locale, String imageUrl, String website, float averageMark) {
        super(id);
        this.email = email;
        this.username = username;
        this.passHash = passHash;
        this.role = role;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.status = status;
        this.locale = locale;
        this.imageUrl = imageUrl;
        this.website = website;
        this.averageMark = averageMark;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public float getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(float averageMark) {
        this.averageMark = averageMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return super.equals(o) &&
                role == user.role &&
                status == user.status &&
                Float.compare(averageMark, user.averageMark) == 0 &&
                nullableEquals(email, user.getEmail()) &&
                nullableEquals(username, user.getUsername()) &&
                nullableEquals(passHash, user.getPassHash()) &&
                nullableEquals(createdAt, user.getCreatedAt()) &&
                nullableEquals(modifiedAt, user.getModifiedAt()) &&
                nullableEquals(locale, user.getLocale()) &&
                nullableEquals(imageUrl, user.getImageUrl()) &&
                nullableEquals(website, user.getWebsite());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + nullableHashCode(email);
        result = 17 * result + nullableHashCode(username);
        result = 31 * result + nullableHashCode(passHash);
        result = 17 * result + role;
        result = 31 * result + nullableHashCode(createdAt);
        result = 17 * result + nullableHashCode(modifiedAt);
        result = 31 * result + status;
        result = 17 * result + nullableHashCode(locale);
        result = 31 * result + nullableHashCode(imageUrl);
        result = 17 * result + nullableHashCode(website);
        result = 31 * result + (averageMark != +0.0f ? Float.floatToIntBits(averageMark) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User[id=" + getId() +
                "; email=" + email +
                "; username=" + username +
                "; passHash=" + passHash +
                "; role=" + role +
                "; createdAt=" + createdAt +
                "; modifiedAt=" + modifiedAt +
                "; status=" + status +
                "; locale=" + locale +
                "; imageUrl=" + imageUrl +
                "; website=" + website +
                "; averageMark=" + averageMark +
                "]";
    }
}
