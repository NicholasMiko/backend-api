package com.bhakti.backend_api.repository;

import com.bhakti.backend_api.entity.Priority;
import com.bhakti.backend_api.entity.Task;
import com.bhakti.backend_api.entity.TaskStatus;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findByStatusShouldReturnTasks() {

        Task task =
                new Task();

        task.setTitle(
                "Repository Test"
        );

        task.setDescription(
                "Testing findByStatus"
        );

        task.setStatus(
                TaskStatus.TODO
        );

        task.setPriority(
                Priority.HIGH
        );

        taskRepository.save(
                task
        );

        List<Task> tasks =
                taskRepository.findByStatus(
                        TaskStatus.TODO
                );

        assertTrue(
                tasks.size() >= 1
        );

        assertTrue(
                tasks.stream()
                        .anyMatch(
                                t ->
                                        t.getTitle()
                                                .equals(
                                                        "Repository Test"
                                                )
                        )
        );
    }
}