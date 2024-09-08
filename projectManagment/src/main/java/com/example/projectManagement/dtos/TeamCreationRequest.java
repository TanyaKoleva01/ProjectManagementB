package com.example.projectManagement.dtos;

import lombok.Getter;
import lombok.Setter;

public class TeamCreationRequest {
    @Getter
    @Setter
    private String teamName;

    @Getter
    @Setter
    private String projectName;

    @Getter
    @Setter
    private Long userId;
}
