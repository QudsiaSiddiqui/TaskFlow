package com.task.taskflow_api.service.interfaces;

import java.util.List;

import com.task.taskflow_api.dto.request.CreateTaskRequest;
import com.task.taskflow_api.dto.request.UpdateTaskRequest;
import com.task.taskflow_api.dto.request.UpdateTaskStatusRequest;
import com.task.taskflow_api.dto.response.TaskResponse;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);

    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, UpdateTaskRequest request);

    void deleteTask(Long id);

    TaskResponse updateTaskStatus(
            Long id,
            UpdateTaskStatusRequest request
    );

    TaskResponse markTaskCompleted(Long id);
}