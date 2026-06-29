package com.bhakti.backend_api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskRequest {

@NotBlank(message = "Title is required")
@Size(max = 100, message = "Title cannot exceed 100 characters")
private String title;

@NotBlank(message = "Description is required")
@Size(max = 500, message = "Description cannot exceed 500 characters")
private String description;

@NotBlank(message = "Priority is required")
private String priority;

private LocalDate dueDate;

public TaskRequest() {
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

public String getPriority() {
    return priority;
}

public void setPriority(
        String priority
) {
    this.priority = priority;
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