package com.bhakti.backend_api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskResponse {

private Long id;

private String title;

private String description;

private String status;

private String priority;

private LocalDateTime createdAt;

private LocalDateTime updatedAt;

private LocalDate dueDate;

public TaskResponse() {
}

public Long getId() {
    return id;
}

public void setId(
        Long id
) {
    this.id = id;
}

public String getTitle() {
    return title;
}

public void setTitle(
        String title
) {
    this.title = title;
}

public String getDescription() {
    return description;
}

public void setDescription(
        String description
) {
    this.description = description;
}

public String getStatus() {
    return status;
}

public void setStatus(
        String status
) {
    this.status = status;
}

public String getPriority() {
    return priority;
}

public void setPriority(
        String priority
) {
    this.priority = priority;
}

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(
        LocalDateTime createdAt
) {
    this.createdAt = createdAt;
}

public LocalDateTime getUpdatedAt() {
    return updatedAt;
}

public void setUpdatedAt(
        LocalDateTime updatedAt
) {
    this.updatedAt = updatedAt;
}

public LocalDate getDueDate() {
    return dueDate;
}

public void setDueDate(
        LocalDate dueDate
) {
    this.dueDate = dueDate;
}

}