package com.vw.commentengine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CommentTarget.
 */
@Entity
@Table(name = "comment_target")
public class CommentTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "target_entity_name")
    private String targetEntityName;

    @OneToMany(mappedBy = "commentTarget")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetEntityName() {
        return targetEntityName;
    }

    public CommentTarget targetEntityName(String targetEntityName) {
        this.targetEntityName = targetEntityName;
        return this;
    }

    public void setTargetEntityName(String targetEntityName) {
        this.targetEntityName = targetEntityName;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public CommentTarget comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public CommentTarget addComments(Comment comment) {
        this.comments.add(comment);
        comment.setCommentTarget(this);
        return this;
    }

    public CommentTarget removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setCommentTarget(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentTarget commentTarget = (CommentTarget) o;
        if (commentTarget.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentTarget.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentTarget{" +
            "id=" + getId() +
            ", targetEntityName='" + getTargetEntityName() + "'" +
            "}";
    }
}
