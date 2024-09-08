package com.example.projectManagement.controllers;

import com.example.projectManagement.dtos.CreateTaskDTO;
import com.example.projectManagement.models.Sprint;
import com.example.projectManagement.models.Task;
import com.example.projectManagement.models.TaskState;
import com.example.projectManagement.models.User;
import com.example.projectManagement.repositories.SprintRepository;
import com.example.projectManagement.repositories.TaskRepository;
import com.example.projectManagement.repositories.TeamRepository;
import com.example.projectManagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final SprintRepository sprintRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksForUser(@PathVariable Long userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // GET /tasks - За получаване на списък с всички задачи
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // GET /tasks/{id} - За получаване на информация за конкретна задача
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /tasks - За създаване на нова задача
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    // DELETE /tasks/{id} - За изтриване на конкретна задача
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/with-sprint-and-user")
    public ResponseEntity<Task> createTaskWithSprintAndUser(@RequestBody CreateTaskDTO createTaskDTO) {
        Optional<User> user = userRepository.findByUsername(createTaskDTO.getUserName());
        Optional<Sprint> sprint = sprintRepository.findByName(createTaskDTO.getSprintName());

        if (user.isEmpty() || sprint.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Task task = new Task();
        task.setName(createTaskDTO.getName());
        task.setDescription(createTaskDTO.getDescription());
        task.setComment(createTaskDTO.getComment());
        task.setTaskType(createTaskDTO.getTaskType());
        task.setTaskState(TaskState.IN_BACKLOG);
        task.setUser(user.get());
        task.setSprint(sprint.get());

        sprint.get().getTasks().add(task);

        Task savedTask = taskRepository.save(task);
        sprintRepository.save(sprint.get());

        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<Task> finishTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTaskState(TaskState.DONE);
            Task savedTask = taskRepository.save(task);
            return new ResponseEntity<>(savedTask, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}