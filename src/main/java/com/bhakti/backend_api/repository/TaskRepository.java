package com.bhakti.backend_api.repository;

import com.bhakti.backend_api.entity.Task;
import com.bhakti.backend_api.entity.TaskStatus;
import com.bhakti.backend_api.entity.User;
import com.bhakti.backend_api.entity.Priority;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository
extends JpaRepository<Task, Long> {

List<Task> findByUser(
        User user
);

List<Task> findByStatus(
        TaskStatus status
);

List<Task> findByPriority(
        Priority priority
);

List<Task> findByTitleContainingIgnoreCase(
        String keyword
);

List<Task> findByDueDateBeforeAndStatusNot(
        LocalDate date,
        TaskStatus status
);

}
