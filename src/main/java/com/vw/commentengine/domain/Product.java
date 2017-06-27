package com.vw.commentengine.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
