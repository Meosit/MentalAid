package by.mksn.epam.mentalaid.entity;

import java.sql.Timestamp;

import static by.mksn.epam.mentalaid.util.NullUtil.nullableEquals;
import static by.mksn.epam.mentalaid.util.NullUtil.nullableHashCode;

public class Mark extends Entity {

    private long userId;
    private long answerId;
    private int value;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    public Mark(long id, long userId, long answerId, int value, Timestamp createdAt, Timestamp modifiedAt) {
        super(id);
        this.userId = userId;
        this.answerId = answerId;
        this.value = value;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Mark() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
        if (!(o instanceof Mark)) return false;

        Mark mark = (Mark) o;

        return super.equals(o) &&
                userId == mark.userId &&
                answerId == mark.answerId &&
                value == mark.value &&
                nullableEquals(createdAt, mark.createdAt) &&
                nullableEquals(modifiedAt, mark.modifiedAt);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 17 * result + (int) (answerId ^ (answerId >>> 32));
        result = 31 * result + value;
        result = 17 * result + nullableHashCode(createdAt);
        result = 31 * result + nullableHashCode(modifiedAt);
        return result;
    }

    @Override
    public String toString() {
        return "Mark[" +
                "userId=" + userId +
                ", answerId=" + answerId +
                ", value=" + value +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ']';
    }
}
