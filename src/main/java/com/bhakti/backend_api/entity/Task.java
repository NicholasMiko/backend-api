package com.bhakti.backend_api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String title;

private String description;

@Enumerated(EnumType.STRING)
private TaskStatus status;

@Enumerated(EnumType.STRING)
private Priority priority;

private LocalDate dueDate;

private LocalDateTime createdAt;

private LocalDateTime updatedAt;

@ManyToOne
@JoinColumn(name = "user_id")
private User user;

@PrePersist
public void onCreate() {

    createdAt =
            LocalDateTime.now();

    updatedAt =
            LocalDateTime.now();
}

@PreUpdate
public void onUpdate() {

    updatedAt =
            LocalDateTime.now();
}

public Task() {
}

public Long getId() {
    return id;
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

public TaskStatus getStatus() {
    return status;
}

public void setStatus(
        TaskStatus status
) {
    this.status = status;
}

public Priority getPriority() {
    return priority;
}

public void setPriority(
        Priority priority
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

public User getUser() {
    return user;
}

public void setUser(
        User user
) {
    this.user = user;
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