package com.josemeurer.devcourses.entities;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_user_review")
public class UserReview implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant created;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant modified;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    public UserReview() {
    }

    public UserReview(Long id, Integer rating, String content, Instant created, Instant modified, User author, Offer offer) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.author = author;
        this.offer = offer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreated() {
        return created;
    }

    @PrePersist
    public void prePersist() {
        created = Instant.now();
    }

    public Instant getModified() {
        return modified;
    }

    @PostPersist
    public void postPersist() {
        modified = Instant.now();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReview that = (UserReview) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
