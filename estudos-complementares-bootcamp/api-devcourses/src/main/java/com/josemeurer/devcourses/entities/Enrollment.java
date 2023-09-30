package com.josemeurer.devcourses.entities;

import com.josemeurer.devcourses.entities.pk.EnrollmentPK;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_enrollment")
public class Enrollment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private EnrollmentPK id = new EnrollmentPK();

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant enrollMoment;
    private boolean available;

    @OneToMany(mappedBy = "enrollment")
    private Set<Deliver> delivers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_lessons_done",
            joinColumns = {
                @JoinColumn(name = "user_id"),
                @JoinColumn(name = "offer_id")
            },
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson> lessonsDone = new HashSet<>();

    public Enrollment() {
    }

    public Enrollment(EnrollmentPK id, Instant enrollMoment, boolean available) {
        this.id = id;
        this.enrollMoment = enrollMoment;
        this.available = available;
    }

    public Enrollment(User user, Offer offer, Instant enrollMoment, boolean available) {
        id.setUser(user);
        id.setOffer(offer);
        this.enrollMoment = enrollMoment;
        this.available = available;
    }

    public EnrollmentPK getId() {
        return id;
    }

    public void setId(EnrollmentPK id) {
        this.id = id;
    }

    public User getUser() {
        return id.getUser();
    }

    public void setUser(User user) {
        id.setUser(user);
    }

    public Offer getOffer() {
        return id.getOffer();
    }

    public void setOffer(Offer offer) {
        id.setOffer(offer);
    }

    public Instant getEnrollMoment() {
        return enrollMoment;
    }

    public void setEnrollMoment(Instant enrollMoment) {
        this.enrollMoment = enrollMoment;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Set<Lesson> getLessonsDone() {
        return lessonsDone;
    }

    public Set<Deliver> getDelivers() {
        return delivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
