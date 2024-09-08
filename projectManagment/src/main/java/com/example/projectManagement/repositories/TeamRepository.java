package com.example.projectManagement.repositories;

import com.example.projectManagement.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
