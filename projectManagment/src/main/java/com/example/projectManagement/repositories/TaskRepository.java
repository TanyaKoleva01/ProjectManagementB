package com.example.projectManagement.repositories;

import com.example.projectManagement.models.Sprint;
import com.example.projectManagement.models.Task;
import com.example.projectManagement.models.TaskState;
import com.example.projectManagement.models.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task, Long> {

    @RestResource
    List<Task> getAllTasksByTaskState(TaskState taskState);

    @RestResource
    List<Task> getAllTasksByTaskType(TaskType taskType);

    List<Task> getAllTasksBySprint(Sprint sprint);

    // Метод за извличане на задачи по Sprint ID
    List<Task> findBySprintId(Long sprintId);

    // Метод за извличане на задачи по User ID
    List<Task> findAllByUserId(Long userId);  // Промяна на типа на Long
}