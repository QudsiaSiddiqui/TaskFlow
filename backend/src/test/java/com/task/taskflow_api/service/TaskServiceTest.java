package com.task.taskflow_api.service;

import com.task.taskflow_api.dto.request.CreateTaskRequest;
import com.task.taskflow_api.dto.response.TaskResponse;
import com.task.taskflow_api.entity.Task;
import com.task.taskflow_api.entity.User;
import com.task.taskflow_api.enums.TaskStatus;
import com.task.taskflow_api.repository.TaskRepository;
import com.task.taskflow_api.repository.UserRepository;
import com.task.taskflow_api.service.impl.TaskServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User mockUser;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockUser = new User();

        mockUser.setName("name");
        mockUser.setEmail("name@example.com");
        mockUser.setPassword("encoded-password");

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "name@example.com",
                        null,
                        null
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }

    @Test
    void shouldCreateTaskSuccessfully() {

        CreateTaskRequest request = new CreateTaskRequest();

        request.setTitle("Finish Backend Project");
        request.setDescription("Complete task management API");
        request.setDueDate(LocalDateTime.now().plusDays(2));

        when(userRepository.findByEmail("name@example.com"))
                .thenReturn(Optional.of(mockUser));

        Task savedTask = new Task();

        savedTask.setTitle(request.getTitle());
        savedTask.setDescription(request.getDescription());
        savedTask.setDueDate(request.getDueDate());
        savedTask.setStatus(TaskStatus.PENDING);
        savedTask.setUser(mockUser);
        savedTask.setCreatedAt(LocalDateTime.now());
        savedTask.setUpdatedAt(LocalDateTime.now());

        when(taskRepository.save(any(Task.class)))
                .thenReturn(savedTask);

        TaskResponse response = taskService.createTask(request);

        assertNotNull(response);

        assertEquals(
                "Finish Backend Project",
                response.getTitle()
        );

        assertEquals(
                TaskStatus.PENDING,
                response.getStatus()
        );

        verify(taskRepository, times(1))
                .save(any(Task.class));
    }
}