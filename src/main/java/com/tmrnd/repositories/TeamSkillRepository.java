package com.tmrnd.repositories;

import com.tmrnd.entities.TeamSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by kent on 05/06/2018.
 */
public interface TeamSkillRepository extends JpaRepository<TeamSkill, String>, JpaSpecificationExecutor<TeamSkill> {
}
