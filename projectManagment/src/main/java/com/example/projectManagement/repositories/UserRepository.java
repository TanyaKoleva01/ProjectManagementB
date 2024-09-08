package com.example.projectManagement.repositories;

import com.example.projectManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    // Добавяне на метод за проверка на съществуване по username
    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}