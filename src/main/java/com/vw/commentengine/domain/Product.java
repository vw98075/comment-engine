package com.vw.commentengine.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private CommentTarget questionTarget;

    @OneToOne
    @JoinColumn(unique = true)
    private CommentTarget reviewTarget;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommentTarget getQuestionTarget() {
        return questionTarget;
    }

    public Product questionTarget(CommentTarget commentTarget) {
        this.questionTarget = commentTarget;
        return this;
    }

    public void setQuestionTarget(CommentTarget commentTarget) {
        this.questionTarget = commentTarget;
    }

    public CommentTarget getReviewTarget() {
        return reviewTarget;
    }

    public Product reviewTarget(CommentTarget commentTarget) {
        this.reviewTarget = commentTarget;
        return this;
    }

    public void setReviewTarget(CommentTarget commentTarget) {
        this.reviewTarget = commentTarget;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
