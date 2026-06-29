package com.bhakti.backend_api.service;

import com.bhakti.backend_api.dto.TaskRequest;
import com.bhakti.backend_api.dto.TaskResponse;
import com.bhakti.backend_api.entity.Task;
import com.bhakti.backend_api.entity.TaskStatus;
import com.bhakti.backend_api.entity.User;
import com.bhakti.backend_api.repository.TaskRepository;
import com.bhakti.backend_api.repository.UserRepository;

import com.bhakti.backend_api.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TaskService taskService;

    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateTaskShouldReturnAccessDeniedWhenNotOwnerAndNotAdmin() {

        User currentUser =
                new User();

        currentUser.setId(1L);
        currentUser.setRole("USER");

        User owner =
                new User();

        owner.setId(2L);

        Task task =
                new Task();

        task.setUser(owner);

        TaskRequest updateRequest =
                new TaskRequest();

        updateRequest.setTitle("Updated");
        updateRequest.setDescription("Updated");

        when(
                request.getAttribute("email")
        ).thenReturn("user@gmail.com");

        when(
                userRepository.findByEmail(
                        "user@gmail.com"
                )
        ).thenReturn(currentUser);

        when(
                taskRepository.findById(1L)
        ).thenReturn(
                Optional.of(task)
        );

        Object result =
                taskService.updateTask(
                        1L,
                        updateRequest,
                        request
                );

        assertEquals(
                "Access Denied",
                result
        );
    }

    @Test
void updateTaskShouldReturnUpdatedTaskWhenOwner() {

    User currentUser =
            new User();

    currentUser.setId(1L);
    currentUser.setRole("USER");

    Task task =
            new Task();

    task.setStatus(
            TaskStatus.TODO
    );

    task.setUser(currentUser);

    task.setTitle("Old Title");
    task.setDescription("Old Description");

    TaskRequest updateRequest =
        new TaskRequest();

updateRequest.setTitle(
        "New Title"
);

updateRequest.setDescription(
        "New Description"
);

updateRequest.setPriority(
        "MEDIUM"
);

    when(
            request.getAttribute("email")
    ).thenReturn(
            "owner@gmail.com"
    );

    when(
            userRepository.findByEmail(
                    "owner@gmail.com"
            )
    ).thenReturn(
            currentUser
    );

    when(
            taskRepository.findById(
                    1L
            )
    ).thenReturn(
            Optional.of(task)
    );

    when(
            taskRepository.save(task)
    ).thenReturn(
            task
    );

    Object result =
            taskService.updateTask(
                    1L,
                    updateRequest,
                    request
            );

    TaskResponse response =
            (TaskResponse) result;

    assertEquals(
            "New Title",
            response.getTitle()
    );

    assertEquals(
            "New Description",
            response.getDescription()
    );
}

@Test
void deleteTaskShouldReturnAccessDeniedWhenNotOwnerAndNotAdmin() {

    User currentUser =
            new User();

    currentUser.setId(1L);
    currentUser.setRole("USER");

    User owner =
            new User();

    owner.setId(2L);

    Task task =
            new Task();

    task.setStatus(
            TaskStatus.TODO
    );

    task.setUser(owner);

    when(
            request.getAttribute("email")
    ).thenReturn(
            "user@gmail.com"
    );

    when(
            userRepository.findByEmail(
                    "user@gmail.com"
            )
    ).thenReturn(
            currentUser
    );

    when(
            taskRepository.findById(
                    1L
            )
    ).thenReturn(
            Optional.of(task)
    );

    String result =
            taskService.deleteTask(
                    1L,
                    request
            );

    assertEquals(
            "Access Denied",
            result
    );
}

@Test
void deleteTaskShouldReturnSuccessWhenOwner() {

    User currentUser =
            new User();

    currentUser.setId(1L);
    currentUser.setRole("USER");

    Task task =
            new Task();

    task.setStatus(
            TaskStatus.TODO
    );

    task.setUser(currentUser);

    when(
            request.getAttribute("email")
    ).thenReturn(
            "owner@gmail.com"
    );

    when(
            userRepository.findByEmail(
                    "owner@gmail.com"
            )
    ).thenReturn(
            currentUser
    );

    when(
            taskRepository.findById(
                    1L
            )
    ).thenReturn(
            Optional.of(task)
    );

    String result =
            taskService.deleteTask(
                    1L,
                    request
            );

    assertEquals(
            "Task deleted successfully",
            result
    );
}

@Test
void updateTaskShouldReturnTaskNotFound() {

    TaskRequest updateRequest =
            new TaskRequest();

    updateRequest.setTitle(
            "Updated Title"
    );

    updateRequest.setDescription(
            "Updated Description"
    );

    when(
            request.getAttribute("email")
    ).thenReturn(
            "user@gmail.com"
    );

    User currentUser =
            new User();

    currentUser.setRole(
            "USER"
    );

    when(
            userRepository.findByEmail(
                    "user@gmail.com"
            )
    ).thenReturn(
            currentUser
    );

    when(
            taskRepository.findById(
                    999L
            )
    ).thenReturn(
            Optional.empty()
    );

   assertThrows(
        ResourceNotFoundException.class,
        () -> taskService.updateTask(
                999L,
                updateRequest,
                request
        )
);
}

@Test
void createTaskShouldCreateTaskSuccessfully() {

    User user =
            new User();

    user.setEmail(
            "user@gmail.com"
    );

   TaskRequest requestBody =
        new TaskRequest();

requestBody.setTitle(
        "Learn Testing"
);

requestBody.setDescription(
        "Mockito Practice"
);

requestBody.setPriority(
        "HIGH"
);

    Task savedTask =
            new Task();

    savedTask.setStatus(
            TaskStatus.TODO
    );

    savedTask.setTitle(
            "Learn Testing"
    );

    savedTask.setDescription(
            "Mockito Practice"
    );

    when(
            request.getAttribute(
                    "email"
            )
    ).thenReturn(
            "user@gmail.com"
    );

    when(
            userRepository.findByEmail(
                    "user@gmail.com"
            )
    ).thenReturn(
            user
    );

    when(
            taskRepository.save(
                    any(Task.class)
            )
    ).thenReturn(
            savedTask
    );

    TaskResponse result =
            taskService.createTask(
                    requestBody,
                    request
            );

    assertEquals(
            "Learn Testing",
            result.getTitle()
    );

    assertEquals(
            "Mockito Practice",
            result.getDescription()
    );
}

}