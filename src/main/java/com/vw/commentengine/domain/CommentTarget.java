package com.vw.commentengine.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Comment> comments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentTarget)) {
            return false;
        }
        return id != null && id.equals(((CommentTarget) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentTarget{" +
            "id=" + getId() +
            ", targetEntityName='" + getTargetEntityName() + "'" +
            "}";
    }
}
