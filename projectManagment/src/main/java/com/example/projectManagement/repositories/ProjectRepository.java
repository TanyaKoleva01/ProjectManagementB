package com.example.projectManagement.repositories;

import com.example.projectManagement.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String name);
}
