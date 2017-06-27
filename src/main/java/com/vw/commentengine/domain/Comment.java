package com.vw.commentengine.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "text")
    private String text;

    @NotNull
    @Column(name = "submitted_date", nullable = false)
    private ZonedDateTime submittedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private CommentTarget target;

    @ManyToOne
    private CommentTarget commentTarget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Comment text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getSubmittedDate() {
        return submittedDate;
    }

    public Comment submittedDate(ZonedDateTime submittedDate) {
        this.submittedDate = submittedDate;
        return this;
    }

    public void setSubmittedDate(ZonedDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    public CommentTarget getTarget() {
        return target;
    }

    public Comment target(CommentTarget commentTarget) {
        this.target = commentTarget;
        return this;
    }

    public void setTarget(CommentTarget commentTarget) {
        this.target = commentTarget;
    }

    public CommentTarget getCommentTarget() {
        return commentTarget;
    }

    public Comment commentTarget(CommentTarget commentTarget) {
        this.commentTarget = commentTarget;
        return this;
    }

    public void setCommentTarget(CommentTarget commentTarget) {
        this.commentTarget = commentTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", submittedDate='" + getSubmittedDate() + "'" +
            "}";
    }
}
