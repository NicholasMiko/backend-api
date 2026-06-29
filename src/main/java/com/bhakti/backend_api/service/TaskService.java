package com.bhakti.backend_api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;

import com.bhakti.backend_api.dto.TaskStatisticsResponse;
import com.bhakti.backend_api.dto.TaskRequest;
import com.bhakti.backend_api.dto.TaskResponse;
import com.bhakti.backend_api.dto.UpdateStatusRequest;
import com.bhakti.backend_api.entity.Priority;
import com.bhakti.backend_api.entity.Task;
import com.bhakti.backend_api.entity.TaskStatus;
import com.bhakti.backend_api.entity.User;
import com.bhakti.backend_api.repository.TaskRepository;
import com.bhakti.backend_api.repository.UserRepository;
import com.bhakti.backend_api.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {

private final TaskRepository taskRepository;
private final UserRepository userRepository;

public TaskService(
        TaskRepository taskRepository,
        UserRepository userRepository
) {
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
}

public List<TaskResponse> getAllTasks() {

    return taskRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
}

public List<TaskResponse> getAllTasks(
        Integer page,
        Integer size
) {

    Page<Task> tasks =
            taskRepository.findAll(
                    PageRequest.of(
                            page,
                            size
                    )
            );

    return tasks.getContent()
            .stream()
            .map(this::toResponse)
            .toList();
}

public List<TaskResponse> getAllTasksSorted(
        String sortBy
) {

    return taskRepository.findAll(
                    Sort.by(sortBy)
            )
            .stream()
            .map(this::toResponse)
            .toList();
}

public List<TaskResponse> getTasksByStatus(
        String status
) {

    return taskRepository.findByStatus(
                    TaskStatus.valueOf(
                            status
                    )
            )
            .stream()
            .map(this::toResponse)
            .toList();
}

public List<TaskResponse> getTasksByPriority(
        String priority
) {

    return taskRepository.findByPriority(
                    Priority.valueOf(
                            priority
                    )
            )
            .stream()
            .map(this::toResponse)
            .toList();
}


public List<TaskResponse> getTasksByKeyword(
        String keyword
) {

    return taskRepository
            .findByTitleContainingIgnoreCase(
                    keyword
            )
            .stream()
            .map(this::toResponse)
            .toList();
}

public TaskResponse getTaskById(
        Long id
) {

    System.out.println(
            "GET TASK ID = " + id
    );

    Task task =
            taskRepository.findById(id)
                    .orElse(null);

    System.out.println(
            "TASK = " + task
    );

    if (task == null) {

        System.out.println(
                "TASK NOT FOUND"
        );

        throw new ResourceNotFoundException(
                "Task with id " + id + " not found"
        );
    }

    return toResponse(task);
}

public List<TaskResponse> getMyTasks(
        HttpServletRequest request
) {

    String email =
            (String) request.getAttribute("email");

    User user =
            userRepository.findByEmail(email);

    return taskRepository.findByUser(user)
            .stream()
            .map(this::toResponse)
            .toList();
}

public TaskResponse createTask(
        TaskRequest request,
        HttpServletRequest httpRequest
) {

    String email =
            (String) httpRequest.getAttribute("email");

    User user =
            userRepository.findByEmail(email);

    Task task = new Task();

    task.setTitle(
            request.getTitle()
    );

    task.setDescription(
            request.getDescription()
    );

    task.setStatus(
            TaskStatus.TODO
    );

    task.setPriority(
            Priority.valueOf(
                    request.getPriority()
            )
    );

    task.setDueDate(
        request.getDueDate()
);
    task.setUser(user);

    Task savedTask =
            taskRepository.save(task);

    return toResponse(savedTask);
}

public Object updateTask(
        Long id,
        TaskRequest updatedTask,
        HttpServletRequest request
) {

    String email =
            (String) request.getAttribute("email");

    User currentUser =
            userRepository.findByEmail(email);

    Task task =
            taskRepository.findById(id)
                    .orElse(null);

    if (task == null) {
        throw new ResourceNotFoundException(
        "Task with id " + id + " not found"
);
    }

    boolean isOwner =
            task.getUser().getId()
                    .equals(currentUser.getId());

    boolean isAdmin =
            "ADMIN".equals(currentUser.getRole());

    if (!isOwner && !isAdmin) {
        return "Access Denied";
    }

    task.setTitle(
            updatedTask.getTitle()
    );

    task.setDescription(
            updatedTask.getDescription()
    );

    task.setPriority(
            Priority.valueOf(
                    updatedTask.getPriority()
            )
    );

    task.setDueDate(
        updatedTask.getDueDate()
);

    Task savedTask =
            taskRepository.save(task);

    return toResponse(savedTask);
}

public Object updateStatus(
        Long id,
        UpdateStatusRequest request,
        HttpServletRequest httpRequest
) {

    String email =
            (String) httpRequest.getAttribute("email");

    User currentUser =
            userRepository.findByEmail(email);

    Task task =
            taskRepository.findById(id)
                    .orElse(null);

    if (task == null) {
        throw new ResourceNotFoundException(
        "Task with id " + id + " not found"
);
    }

    boolean isOwner =
            task.getUser().getId()
                    .equals(currentUser.getId());

    boolean isAdmin =
            "ADMIN".equals(currentUser.getRole());

    if (!isOwner && !isAdmin) {
        return "Access Denied";
    }

    task.setStatus(
            TaskStatus.valueOf(
                    request.getStatus()
            )
    );

    Task savedTask =
            taskRepository.save(task);

    return toResponse(savedTask);
}

public String deleteTask(
        Long id,
        HttpServletRequest request
) {

    String email =
            (String) request.getAttribute("email");

    User currentUser =
            userRepository.findByEmail(email);

    Task task =
            taskRepository.findById(id)
                    .orElse(null);

    if (task == null) {
        throw new ResourceNotFoundException(
        "Task with id " + id + " not found"
);
    }

    boolean isOwner =
            task.getUser().getId()
                    .equals(currentUser.getId());

    boolean isAdmin =
            "ADMIN".equals(currentUser.getRole());

    if (!isOwner && !isAdmin) {
        return "Access Denied";
    }

    taskRepository.delete(task);

    return "Task deleted successfully";
}

public TaskStatisticsResponse getTaskStatistics() {

    TaskStatisticsResponse response =
            new TaskStatisticsResponse();

    response.setTotalTasks(
            taskRepository.count()
    );

    response.setTodo(
            taskRepository.findByStatus(
                    TaskStatus.TODO
            ).size()
    );

    response.setInProgress(
            taskRepository.findByStatus(
                    TaskStatus.IN_PROGRESS
            ).size()
    );

    response.setDone(
            taskRepository.findByStatus(
                    TaskStatus.DONE
            ).size()
    );

    long totalTasks =
            taskRepository.count();

    long doneTasks =
            taskRepository.findByStatus(
                    TaskStatus.DONE
            ).size();

    double completionRate = 0;

if (totalTasks > 0) {
    completionRate =
            (doneTasks * 100.0)
                    / totalTasks;

    completionRate =
            Math.round(
                    completionRate * 100
            ) / 100.0;
}

    response.setCompletionRate(
            completionRate
    );

    return response;
}

public List<TaskResponse> getOverdueTasks() {

    return taskRepository
            .findByDueDateBeforeAndStatusNot(
                    LocalDate.now(),
                    TaskStatus.DONE
            )
            .stream()
            .map(this::toResponse)
            .toList();
}

private TaskResponse toResponse(
        Task task
) {

    TaskResponse response =
            new TaskResponse();

    response.setId(
            task.getId()
    );

    response.setTitle(
            task.getTitle()
    );

    response.setDescription(
            task.getDescription()
    );

    response.setStatus(
            task.getStatus().name()
    );

    if (task.getPriority() != null) {
        response.setPriority(
                task.getPriority().name()
        );
    }

    response.setCreatedAt(
            task.getCreatedAt()
    );

    response.setUpdatedAt(
            task.getUpdatedAt()
    );

    response.setDueDate(
        task.getDueDate()
);


    return response;
}


}
