package com.example.projectManagement.dtos;

public class UpdateProjectStatusRequest {
    private Long projectId;
    private String status;

    // Getters and setters
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
