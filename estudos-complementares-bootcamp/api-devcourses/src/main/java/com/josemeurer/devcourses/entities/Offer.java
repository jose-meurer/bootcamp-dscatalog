package com.josemeurer.devcourses.entities;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_offer")
public class Offer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String edition;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
    private Set<UserReview> userReviews = new HashSet<>();

    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
    private Set<Resource> resources = new HashSet<>();

    public Offer() {
    }

    public Offer(Long id, String edition, Instant creationDate, Course course) {
        this.id = id;
        this.edition = edition;
        this.creationDate = creationDate;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    @PrePersist
    public void prePersist() {
        creationDate = Instant.now();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<UserReview> getUserReviews() {
        return userReviews;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(id, offer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
