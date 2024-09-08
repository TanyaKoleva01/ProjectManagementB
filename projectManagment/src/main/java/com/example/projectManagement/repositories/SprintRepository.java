package com.example.projectManagement.repositories;

import com.example.projectManagement.models.Sprint;
import com.example.projectManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

    Sprint getSprintByName(String name);
    Optional<Sprint> findByName(String name);
}
