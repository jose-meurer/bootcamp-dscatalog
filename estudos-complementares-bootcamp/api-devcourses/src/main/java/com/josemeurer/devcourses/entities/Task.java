package com.josemeurer.devcourses.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tb_task")
public class Task extends Lesson implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer questionCount;
    private Integer approvalCount;

    public Task() {
    }

    public Task(Long id, String title, Integer position, Section section, String description, Integer questionCount, Integer approvalCount) {
        super(id, title, position, section);
        this.description = description;
        this.questionCount = questionCount;
        this.approvalCount = approvalCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getApprovalCount() {
        return approvalCount;
    }

    public void setApprovalCount(Integer approvalCount) {
        this.approvalCount = approvalCount;
    }
}
