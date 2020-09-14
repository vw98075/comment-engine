package com.vw.commentengine.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(unique = true)
    private CommentTarget feedback;

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

    public Review text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentTarget getFeedback() {
        return feedback;
    }

    public Review feedback(CommentTarget commentTarget) {
        this.feedback = commentTarget;
        return this;
    }

    public void setFeedback(CommentTarget commentTarget) {
        this.feedback = commentTarget;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        }
        return id != null && id.equals(((Review) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Review{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            "}";
    }
}
