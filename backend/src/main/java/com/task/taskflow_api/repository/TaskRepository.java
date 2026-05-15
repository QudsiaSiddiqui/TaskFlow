package com.task.taskflow_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.taskflow_api.entity.Task;
import com.task.taskflow_api.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser(User user);

    Optional<Task> findByIdAndUser(Long id, User user);

}