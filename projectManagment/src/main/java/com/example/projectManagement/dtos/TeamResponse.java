package com.example.projectManagement.dtos;

import com.example.projectManagement.models.Team;
import com.example.projectManagement.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class TeamResponse {
    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private List<User> users;

    @Setter
    @Getter
    private String projectName;

    @Setter
    @Getter
    private String projectStatus;

    @Setter
    @Getter
    private Date projectStartDate;

    @Setter
    @Getter
    private Date projectEndDate;

    @Setter
    @Getter
    private List<Team> projectTeams;

    @Setter
    @Getter
    private String managerName;

    public TeamResponse(Long id, String name, List<User> users, String projectName, String projectStatus, Date projectStartDate, Date projectEndDate, List<Team> projectTeams, String managerName) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.projectName = projectName;
        this.projectStatus = projectStatus;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectTeams = projectTeams;
        this.managerName = managerName;
    }
}