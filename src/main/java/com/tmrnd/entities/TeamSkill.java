package com.tmrnd.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by kent on 05/06/2018.
 */
@Entity
@Table(name = "team_skill")
public class TeamSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id", nullable = false)
    @JsonProperty("TEAM_ID")
    private String teamId;

    @Column(name = "skill", nullable = false)
    @JsonProperty("SKILL")
    private String skill;

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

    @Override
    public String toString() {
        return "TeamSkill{" + "teamId='" + teamId + '\'' + ", skill='" + skill + '\'' + '}';
    }
}
