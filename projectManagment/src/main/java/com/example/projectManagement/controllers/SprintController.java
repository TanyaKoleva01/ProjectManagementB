package com.example.projectManagement.controllers;

import com.example.projectManagement.models.Sprint;
import com.example.projectManagement.repositories.SprintRepository;
import com.example.projectManagement.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sprints")
@AllArgsConstructor
public class SprintController {

    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;

    // Добавяне на задачи към спринт
    @PatchMapping(value = "/{id}/addTasks")
    public ResponseEntity<Void> addTasks(@PathVariable Long id, @RequestBody List<Long> taskIds) {
        var sprintOptional = sprintRepository.findById(id);
        if (sprintOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var sprint = sprintOptional.get();
        var tasks = taskRepository.findAllById(taskIds);
        tasks.forEach(t -> t.setSprint(sprint));
        taskRepository.saveAll(tasks);
        return ResponseEntity.ok().build();
    }

    // Вземане на всички спринтове
    @GetMapping
    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    // Вземане на спринт по ID
    @GetMapping("/{id}")
    public ResponseEntity<Sprint> getSprintById(@PathVariable Long id) {
        return sprintRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint) {
        Sprint savedSprint = sprintRepository.save(sprint);
        return ResponseEntity.status(201).body(savedSprint);
    }



    // Изтриване на спринт заедно със свързаните задачи
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long id) {
        var sprintOptional = sprintRepository.findById(id);
        if (sprintOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Вземане на всички задачи, свързани с този спринт
        var sprint = sprintOptional.get();
        var tasks = taskRepository.findBySprintId(id);

        // Изтриване на задачите
        taskRepository.deleteAll(tasks);

        // Сега изтриваме спринта
        sprintRepository.delete(sprint);

        return ResponseEntity.noContent().build();
    }
}
