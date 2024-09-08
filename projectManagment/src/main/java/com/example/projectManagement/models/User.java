package com.example.projectManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"projects", "teams"})
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String role;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference  // Използвайте за обратна референция към Team
    private Team team;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-task")  // Съответства на JsonBackReference в Task
    private List<Task> tasks;
}
