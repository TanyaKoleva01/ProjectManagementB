package com.example.projectManagement.dtos;

import com.example.projectManagement.models.TaskState;
import com.example.projectManagement.models.TaskType;

public class CreateTaskDTO {
    private String name;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    private String comment;
    private TaskType taskType;
    private TaskState taskState;
    private String sprintName;
    private String userName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
