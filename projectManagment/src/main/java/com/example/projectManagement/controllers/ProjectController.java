package com.example.projectManagement.controllers;

import com.example.projectManagement.dtos.UpdateProjectStatusRequest;
import com.example.projectManagement.models.Project;
import com.example.projectManagement.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;

    // Създаване на нов проект
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectRepository.save(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    // Получаване на всички проекти
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Получаване на проект по ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            return new ResponseEntity<>(projectOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Актуализиране на съществуващ проект
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.setName(projectDetails.getName());
            project.setDescription(projectDetails.getDescription());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            project.setStatus(projectDetails.getStatus());
            Project updatedProject = projectRepository.save(project);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Изтриване на проект
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            projectRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Актуализиране на статуса на проект
    @PostMapping("/update-status")
    public ResponseEntity<Project> updateProjectStatus(@RequestBody UpdateProjectStatusRequest request) {
        Optional<Project> projectOptional = projectRepository.findById(request.getProjectId());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.setStatus(request.getStatus());
            Project updatedProject = projectRepository.save(project);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
