package com.example.projectManagement.dtos;

public class AddUserToTeamRequest {
    private Long teamId;
    private Long userId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
