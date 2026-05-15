package com.task.taskflow_api.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.taskflow_api.dto.request.CreateTaskRequest;
import com.task.taskflow_api.dto.request.UpdateTaskRequest;
import com.task.taskflow_api.dto.request.UpdateTaskStatusRequest;
import com.task.taskflow_api.dto.response.TaskResponse;
import com.task.taskflow_api.entity.Task;
import com.task.taskflow_api.entity.User;
import com.task.taskflow_api.enums.TaskStatus;
import com.task.taskflow_api.exceptions.ResourceNotFoundException;
import com.task.taskflow_api.mapper.TaskMapper;
import com.task.taskflow_api.repository.TaskRepository;
import com.task.taskflow_api.repository.UserRepository;
import com.task.taskflow_api.service.interfaces.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request) {

        // Fetch currently logged-in user
        User currentUser = getCurrentUser();

        Task task = new Task();

        task.setTitle(request.getTitle());

        task.setDescription(request.getDescription());

        task.setDueDate(request.getDueDate());

        // New tasks are created with default PENDING status
        task.setStatus(TaskStatus.PENDING);

        task.setUser(currentUser);

        task.setCreatedAt(LocalDateTime.now());

        task.setUpdatedAt(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);

        return TaskMapper.toResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getAllTasks() {

        User currentUser = getCurrentUser();

        return taskRepository.findAllByUser(currentUser)
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    @Override
    public TaskResponse getTaskById(Long id) {

        User currentUser = getCurrentUser();

        // Ensures users can access only their own tasks
        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        return TaskMapper.toResponse(task);
    }

    @Override
    public TaskResponse updateTask(
            Long id,
            UpdateTaskRequest request
    ) {

        User currentUser = getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        task.setTitle(request.getTitle());

        task.setDescription(request.getDescription());

        task.setDueDate(request.getDueDate());

        // Update timestamp whenever task details change
        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return TaskMapper.toResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {

        User currentUser = getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);
    }

    @Override
    public TaskResponse updateTaskStatus(
            Long id,
            UpdateTaskStatusRequest request
    ) {

        User currentUser = getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        // Shortcut method to directly mark task as completed
        task.setStatus(request.getStatus());

        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return TaskMapper.toResponse(updatedTask);
    }

    @Override
    public TaskResponse markTaskCompleted(Long id) {

        User currentUser = getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        task.setStatus(TaskStatus.COMPLETED);

        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return TaskMapper.toResponse(updatedTask);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

         // Fetch authenticated user using email from security context
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
}