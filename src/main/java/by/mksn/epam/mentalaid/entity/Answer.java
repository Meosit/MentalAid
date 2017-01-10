package by.mksn.epam.mentalaid.entity;

import java.sql.Timestamp;

import static by.mksn.epam.mentalaid.util.NullUtil.nullableEquals;
import static by.mksn.epam.mentalaid.util.NullUtil.nullableHashCode;

/**
 * Represents user answer. Little bit more than appropriate database table.
 * Also contains average mark for this question and it's count.
 */
public class Answer extends Entity {

    /**
     * Represents that this answer is deleted.
     */
    public static final int STATUS_DELETED = -1;
    /**
     * Represents just normal status of a answer, same as "not deleted"
     */
    public static final int STATUS_NORMAL = 0;

    private long questionId;
    private long creatorId;
    private String text;
    private int status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    /**
     * Selected from `mark` table
     */
    private float averageMark;
    /**
     * Selected from `mark` table
     */
    private int markCount;
    /**
     * Selected from `user` table
     */
    private String creatorUsername;

    public Answer(long id, long questionId, long creatorId, String text, int status, Timestamp createdAt, Timestamp modifiedAt, float averageMark, int markCount, String creatorUsername) {
        super(id);
        this.questionId = questionId;
        this.creatorId = creatorId;
        this.text = text;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.averageMark = averageMark;
        this.markCount = markCount;
        this.creatorUsername = creatorUsername;
    }

    public Answer() {
        super(-1);
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public float getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(float averageMark) {
        this.averageMark = averageMark;
    }

    public int getMarkCount() {
        return markCount;
    }

    public void setMarkCount(int markCount) {
        this.markCount = markCount;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return super.equals(o)
                && questionId == answer.questionId
                && creatorId == answer.creatorId
                && Float.compare(answer.averageMark, averageMark) == 0
                && markCount == answer.markCount
                && status == answer.status
                && nullableEquals(text, answer.text)
                && nullableEquals(creatorUsername, answer.creatorUsername)
                && nullableEquals(createdAt, answer.createdAt)
                && nullableEquals(modifiedAt, answer.modifiedAt);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (questionId ^ (questionId >>> 32));
        result = 17 * result + (int) (creatorId ^ (creatorId >>> 32));
        result = 31 * result + nullableHashCode(text);
        result = 17 * result + nullableHashCode(creatorUsername);
        result = 31 * result + nullableHashCode(createdAt);
        result = 17 * result + nullableHashCode(modifiedAt);
        result = 31 * result + (averageMark != +0.0f ? Float.floatToIntBits(averageMark) : 0);
        result = 17 * result + markCount;
        result = 31 * result + status;
        return result;
    }

    @Override
    public String toString() {
        return "Answer[" +
                "id=" + getId() +
                ", questionId=" + questionId +
                ", creatorId=" + creatorId +
                ", text='" + text + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", averageMark=" + averageMark +
                ", markCount=" + markCount +
                ", creatorUsername='" + creatorUsername + '\'' +
                ']';
    }
}
