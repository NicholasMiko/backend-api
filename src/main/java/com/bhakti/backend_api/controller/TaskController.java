package com.bhakti.backend_api.controller;

import com.bhakti.backend_api.dto.TaskRequest;
import com.bhakti.backend_api.dto.TaskResponse;
import com.bhakti.backend_api.dto.UpdateStatusRequest;
import com.bhakti.backend_api.service.TaskService;
import com.bhakti.backend_api.dto.TaskStatisticsResponse;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

private final TaskService taskService;

public TaskController(
        TaskService taskService
) {
    this.taskService = taskService;
}

@GetMapping
public List<TaskResponse> getAllTasks(

        @RequestParam(
                required = false
        ) String status,

        @RequestParam(
                required = false
        ) String priority,

        @RequestParam(
                required = false
        ) String sort,

        @RequestParam(
                required = false
        ) Integer page,

        @RequestParam(
                required = false
        ) Integer size
) {

    if (status != null) {
        return taskService.getTasksByStatus(
                status
        );
    }

    if (priority != null) {
        return taskService.getTasksByPriority(
                priority
        );
    }

    if (sort != null) {
        return taskService.getAllTasksSorted(
                sort
        );
    }

    if (
            page != null &&
            size != null
    ) {
        return taskService.getAllTasks(
                page,
                size
        );
    }

    return taskService.getAllTasks();
}

@GetMapping("/search")
public List<TaskResponse> searchTasks(
        @RequestParam String keyword
) {

    return taskService.getTasksByKeyword(
            keyword
    );
}

@GetMapping("/my")
public List<TaskResponse> getMyTasks(
        HttpServletRequest request
) {
    return taskService.getMyTasks(request);
}


@GetMapping("/statistics")
public TaskStatisticsResponse getTaskStatistics() {

    return taskService.getTaskStatistics();
}

@GetMapping("/overdue")
public List<TaskResponse> getOverdueTasks() {

    return taskService.getOverdueTasks();
}

@GetMapping("/{id}")
public TaskResponse getTaskById(
        @PathVariable Long id
) {
    return taskService.getTaskById(id);
}

@PostMapping
public TaskResponse createTask(
        @Valid @RequestBody TaskRequest request,
        HttpServletRequest httpRequest
) {
    return taskService.createTask(
            request,
            httpRequest
    );
}

@PutMapping("/{id}")
public Object updateTask(
        @PathVariable Long id,
        @Valid @RequestBody TaskRequest updatedTask,
        HttpServletRequest request
) {
    return taskService.updateTask(
            id,
            updatedTask,
            request
    );
}

@PutMapping("/{id}/status")
public Object updateStatus(
        @PathVariable Long id,
        @RequestBody UpdateStatusRequest request,
        HttpServletRequest httpRequest
) {
    return taskService.updateStatus(
            id,
            request,
            httpRequest
    );
}

@DeleteMapping("/{id}")
public String deleteTask(
        @PathVariable Long id,
        HttpServletRequest request
) {
    return taskService.deleteTask(
            id,
            request
    );
}

}