package com.bhakti.backend_api.controller;

import com.bhakti.backend_api.dto.TaskRequest;
import com.bhakti.backend_api.dto.TaskResponse;
import com.bhakti.backend_api.dto.TaskStatisticsResponse;
import com.bhakti.backend_api.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bhakti.backend_api.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void getAllTasksShouldReturnTaskList() throws Exception {

        TaskResponse task =
                new TaskResponse();

        task.setId(1L);

        task.setTitle(
                "Learn Spring Boot"
        );

        task.setDescription(
                "Practice Controller Test"
        );

        task.setStatus(
                "TODO"
        );

        task.setPriority(
                "HIGH"
        );

        when(
                taskService.getAllTasks()
        ).thenReturn(
                List.of(task)
        );

        mockMvc.perform(
                        get("/tasks")
                                .header(
                                        "Authorization",
                                        "Bearer test"
                                )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$[0].title")
                                .value(
                                        "Learn Spring Boot"
                                )
                );
    }

    @Test
    void getTaskByIdShouldReturnTask() throws Exception {

        TaskResponse task =
                new TaskResponse();

        task.setId(1L);

        task.setTitle(
                "Learn Spring Boot"
        );

        task.setDescription(
                "Practice Controller Test"
        );

        task.setStatus(
                "TODO"
        );

        task.setPriority(
                "HIGH"
        );

        when(
                taskService.getTaskById(1L)
        ).thenReturn(
                task
        );

        mockMvc.perform(
                        get("/tasks/1")
                                .header(
                                        "Authorization",
                                        "Bearer test"
                                )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.id")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.title")
                                .value(
                                        "Learn Spring Boot"
                                )
                );
    }

    @Test
    void createTaskShouldReturnCreatedTask() throws Exception {

        TaskRequest request =
                new TaskRequest();

        request.setTitle(
                "Learn Spring Boot"
        );

        request.setDescription(
                "Practice POST API"
        );

        request.setPriority(
                "HIGH"
        );

        TaskResponse response =
                new TaskResponse();

        response.setId(1L);

        response.setTitle(
                "Learn Spring Boot"
        );

        response.setDescription(
                "Practice POST API"
        );

        response.setPriority(
                "HIGH"
        );

        response.setStatus(
                "TODO"
        );

        when(
                taskService.createTask(
                        any(TaskRequest.class),
                        any()
                )
        ).thenReturn(
                response
        );

        mockMvc.perform(
                        post("/tasks")
                                .header(
                                        "Authorization",
                                        "Bearer test"
                                )
                                .contentType(
                                        "application/json"
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.title")
                                .value(
                                        "Learn Spring Boot"
                                )
                )
                .andExpect(
                        jsonPath("$.status")
                                .value(
                                        "TODO"
                                )
                );
    }

    @Test
    void getTaskStatisticsShouldReturnStatistics() throws Exception {

        TaskStatisticsResponse response =
                new TaskStatisticsResponse();

        response.setTotalTasks(21L);
        response.setTodo(19L);
        response.setInProgress(1L);
        response.setDone(1L);

        response.setCompletionRate(
                4.761904761904762
        );

        when(
                taskService.getTaskStatistics()
        ).thenReturn(
                response
        );

        mockMvc.perform(
                        get("/tasks/statistics")
                                .header(
                                        "Authorization",
                                        "Bearer test"
                                )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.totalTasks")
                                .value(21)
                )
                .andExpect(
                        jsonPath("$.done")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.completionRate")
                                .value(
                                        4.761904761904762
                                )
                );
    }

    @Test
void getTaskByIdShouldReturn404WhenTaskNotFound() throws Exception {

    when(
            taskService.getTaskById(
                    999L
            )
    ).thenThrow(
            new ResourceNotFoundException(
                    "Task with id 999 not found"
            )
    );

    mockMvc.perform(
                    get("/tasks/999")
                            .header(
                                    "Authorization",
                                    "Bearer test"
                            )
            )
            .andExpect(
                    status().isNotFound()
            )
            .andExpect(
                    jsonPath("$.status")
                            .value(404)
            )
            .andExpect(
                    jsonPath("$.message")
                            .value(
                                    "Resource Not Found"
                            )
            );
}

@Test
void createTaskShouldReturn400WhenRequestInvalid() throws Exception {

    TaskRequest request =
            new TaskRequest();

    request.setTitle("");
    request.setDescription("");
    request.setPriority("");

    mockMvc.perform(
                    post("/tasks")
                            .header(
                                    "Authorization",
                                    "Bearer test"
                            )
                            .contentType(
                                    "application/json"
                            )
                            .content(
                                    objectMapper.writeValueAsString(
                                            request
                                    )
                            )
            )
            .andExpect(
                    status().isBadRequest()
            );
}

}