package com.tmrnd.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by kent on 05/06/2018.
 */
@Entity
@Table(name = "assignment_result")
public class AssignmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id", nullable = false)
    @JsonProperty("team_id")
    private String teamId;

    @Column(name = "skill", nullable = false)
    @JsonProperty("skill")
    private String skill;

    @Column(name = "task_id", nullable = false)
    @JsonProperty("task_id")
    private String taskId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
