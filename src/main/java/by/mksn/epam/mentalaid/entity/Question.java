package by.mksn.epam.mentalaid.entity;

import java.sql.Timestamp;

import static by.mksn.epam.mentalaid.util.NullUtil.nullableEquals;
import static by.mksn.epam.mentalaid.util.NullUtil.nullableHashCode;

/**
 * Represents user question. Little bit more than appropriate database table.
 * For example there username of creator and count of answers for this question.
 */
public class Question extends Entity {

    /**
     * Represents that this question is deleted.
     */
    public static final int STATUS_DELETED = -1;
    /**
     * Represents just normal status of a question, same as "not deleted"
     */
    public static final int STATUS_NORMAL = 0;

    private long id;
    private long creatorId;
    private String title;
    private String description;
    private int status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    /**
     * Selected from table `user`
     */
    private String creatorUsername;
    /**
     * Selected from `answer` table
     */
    private int answerCount;

    public Question(long id, long creatorId, String title, String description, int status, Timestamp createdAt, Timestamp modifiedAt, String creatorUsername, int answerCount) {
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.creatorUsername = creatorUsername;
        this.answerCount = answerCount;
    }

    public Question() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id == question.id
                && creatorId == question.creatorId
                && status == question.status
                && answerCount == question.answerCount
                && nullableEquals(title, question.title)
                && nullableEquals(description, question.description)
                && nullableEquals(createdAt, question.createdAt)
                && nullableEquals(modifiedAt, question.modifiedAt)
                && nullableEquals(creatorUsername, question.creatorUsername);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (creatorId ^ (creatorId >>> 32));
        result = 17 * result + nullableHashCode(title);
        result = 31 * result + nullableHashCode(description);
        result = 17 * result + status;
        result = 31 * result + nullableHashCode(createdAt);
        result = 17 * result + nullableHashCode(modifiedAt);
        result = 31 * result + nullableHashCode(creatorUsername);
        result = 17 * result + answerCount;
        return result;
    }

    @Override
    public String toString() {
        return "Question[" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", creatorUsername='" + creatorUsername + '\'' +
                ", answerCount=" + answerCount +
                ']';
    }
}
