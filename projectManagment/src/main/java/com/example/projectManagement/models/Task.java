package com.example.projectManagement.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;

    private String comment;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskType taskType = TaskType.TASK;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskState taskState = TaskState.IN_BACKLOG;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-task")  // Уникално име за референция
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sprint_id")
    @JsonBackReference(value = "sprint-task")  // Уникално име за референция
    private Sprint sprint;
}
