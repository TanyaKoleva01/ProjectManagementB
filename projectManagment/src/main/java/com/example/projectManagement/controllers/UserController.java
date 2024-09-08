package com.example.projectManagement.controllers;

import com.example.projectManagement.dtos.LoginRequest;
import com.example.projectManagement.models.User;
import com.example.projectManagement.models.Task;
import com.example.projectManagement.repositories.TaskRepository;
import com.example.projectManagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET /users - Получаване на списък с всички потребители
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // GET /users/{id} - Получаване на информация за конкретен потребител
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /users/login
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<User> getUserByEmail(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(request.getPassword())) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /users - Създаване на нов потребител
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // PATCH /users/{id}/assignTasks - Присвояване на задачи на потребител
    @PatchMapping(value = "/{id}/assignTasks", consumes = "application/json")
    public ResponseEntity<Void> assignTasks(@PathVariable Long id, @RequestBody List<Long> taskIds) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Task> tasks = taskRepository.findAllById(taskIds);
            tasks.forEach(task -> task.setUser(user));
            taskRepository.saveAll(tasks);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /users/{id} - Изтриване на потребител
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            // Първо изтриваме всички задачи, свързани с потребителя
            List<Task> tasks = taskRepository.findAllByUserId(id);
            taskRepository.deleteAll(tasks);

            // След това изтриваме самия потребител
            userRepository.delete(userOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
