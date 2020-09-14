package com.vw.commentengine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

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
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private CommentTarget commentTarget;

    // jhipster-needle-entity-add-field - JHipster will add fields here
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", submittedDate='" + getSubmittedDate() + "'" +
            "}";
    }
}
