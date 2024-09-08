package com.example.projectManagement.controllers;

import com.example.projectManagement.dtos.AddUserToTeamRequest;
import com.example.projectManagement.dtos.TeamCreationRequest;
import com.example.projectManagement.dtos.TeamResponse;
import com.example.projectManagement.models.Team;
import com.example.projectManagement.models.User;
import com.example.projectManagement.models.Project;
import com.example.projectManagement.repositories.TeamRepository;
import com.example.projectManagement.repositories.UserRepository;
import com.example.projectManagement.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamController {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @PostMapping("/adduser")
    public ResponseEntity<?> addUserToTeam(@RequestBody AddUserToTeamRequest requestBody) {
        Team team = teamRepository.findById(requestBody.getTeamId()).orElse(null);
        if (team == null) {
            return ResponseEntity.badRequest().body("Team not found");
        }

        User user = userRepository.findById(requestBody.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        team.getUsers().add(user);
        teamRepository.save(team);

        user.setTeam(team);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/removeuser")
    public ResponseEntity<?> removeUserFromTeam(@RequestBody AddUserToTeamRequest requestBody) {
        Team team = teamRepository.findById(requestBody.getTeamId()).orElse(null);
        if (team == null) {
            return ResponseEntity.badRequest().body("Team not found");
        }

        User user = userRepository.findById(requestBody.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (Objects.equals(user.getId(), team.getManagerId())) {
            return ResponseEntity.badRequest().body("Cant remove the manager!");
        }
        user.setTeam(null);  // Премахване на връзката с екипа от потребителя
        userRepository.save(user);  // Запазване на потребителя след промяната
        team.getUsers().remove(user);
        teamRepository.save(team);



        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        Team team = teamRepository.findById(id).orElseThrow();
        Project project = team.getProject();
        User manager = userRepository.findById(team.getManagerId()).orElseThrow();

        TeamResponse response = new TeamResponse(
                team.getId(),
                team.getName(),
                team.getUsers(),
                project.getName(),
                project.getStatus(),
                project.getStartDate(),
                project.getEndDate(),
                project.getTeams(),
                manager.getUsername()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamCreationRequest teamCreationRequest) {
        String teamName = teamCreationRequest.getTeamName();
        String projectName = teamCreationRequest.getProjectName();
        Long userId = teamCreationRequest.getUserId();

        // Find the project ID based on the project name
        Project project = projectRepository.findByName(projectName);
        if (project == null) {
            return ResponseEntity.badRequest().body("Project not found");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Create the team
        Team team = new Team();
        team.setManagerId(userId);
        team.setName(teamName);
        team.setProject(project);
        team.getUsers().add(user);

        // Update project teams
        project.getTeams().add(team);
        projectRepository.save(project);

        teamRepository.save(team);

        user.setTeam(team);
        userRepository.save(user);

        return ResponseEntity.ok("Team created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/assignUser")
    public ResponseEntity<Void> assignUserToTeam(@PathVariable Long id, @RequestBody Long userId) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (teamOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Team team = teamOptional.get();
        User user = userOptional.get();
        if (!team.getUsers().contains(user)) {
            team.getUsers().add(user);
            teamRepository.save(team);
        }
        return ResponseEntity.ok().build();
    }
}
