package com.tmrnd.repositories;

import com.tmrnd.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by kent on 05/06/2018.
 */
public interface TaskRepository extends JpaRepository<Task, String>, JpaSpecificationExecutor<Task> {
    Task getByTaskId(String taskId);
}
