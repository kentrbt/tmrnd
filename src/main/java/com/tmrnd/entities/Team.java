package com.tmrnd.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by kent on 05/06/2018.
 */
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id", nullable = false, unique = true)
    @JsonProperty("TEAM_ID")
    private String teamId;

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

    @Override
    public String toString() {
        return "Team{teamId='" + teamId + '\'' + '}';
    }
}
