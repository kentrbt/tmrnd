package com.tmrnd.repositories;

import com.tmrnd.entities.AssignmentResult;
import com.tmrnd.entities.TeamSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kent on 05/06/2018.
 */
public interface AssignmentResultRepository
        extends JpaRepository<AssignmentResult, String>, JpaSpecificationExecutor<AssignmentResult> {

    @Transactional
    @Modifying
    @Query(value = "insert into assignment_result(team_id, skill, task_id) \n" +
            "select t.team_id, ts.skill, tk.task_id from team t\n" +
            "inner join team_skill ts on ts.team_id = t.team_id\n" + "inner join task tk on tk.skill = ts.skill\n",
           nativeQuery = true)
    void assignTask();
}
