package com.tmrnd.repositories;

import com.tmrnd.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by kent on 05/06/2018.
 */
public interface TeamRepository extends JpaRepository<Team, String>, JpaSpecificationExecutor<Team> {
    Team getByTeamId(String teamId);
}
