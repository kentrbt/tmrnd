package com.tmrnd.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

/**
 * Created by kent on 05/06/2018.
 */
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id", nullable = false, unique = true)
    @JsonProperty("TASK_ID")
    private String taskId;

    @Column(name = "skill", nullable = false)
    @JsonProperty("SKILL")
    private String skill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Task{" + "taskId='" + taskId + '\'' + ", skill='" + skill + '\'' + '}';
    }
}
