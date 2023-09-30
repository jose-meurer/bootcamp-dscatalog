package com.josemeurer.devcourses.entities;

import com.josemeurer.devcourses.entities.enums.DeliverStatus;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_deliver")
public class Deliver implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uri;
    private DeliverStatus status;
    private String feedback;
    private Integer correctCount;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
    })
    private Enrollment enrollment;

    public Deliver() {
    }

    public Deliver(Long id, String uri, DeliverStatus status, String feedback, Integer correctCount, Instant moment, Lesson lesson, Enrollment enrollment) {
        this.id = id;
        this.uri = uri;
        this.status = status;
        this.feedback = feedback;
        this.correctCount = correctCount;
        this.moment = moment;
        this.lesson = lesson;
        this.enrollment = enrollment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public DeliverStatus getStatus() {
        return status;
    }

    public void setStatus(DeliverStatus status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deliver deliver = (Deliver) o;
        return Objects.equals(id, deliver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
