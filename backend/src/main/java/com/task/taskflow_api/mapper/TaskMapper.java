package com.task.taskflow_api.mapper;

import com.task.taskflow_api.dto.response.TaskResponse;
import com.task.taskflow_api.entity.Task;

public class TaskMapper {

    private TaskMapper() {
    }

    public static TaskResponse toResponse(Task task) {

        TaskResponse response = new TaskResponse();

        response.setId(task.getId());

        response.setTitle(task.getTitle());

        response.setDescription(task.getDescription());

        response.setStatus(task.getStatus());

        response.setDueDate(task.getDueDate());

        response.setCreatedAt(task.getCreatedAt());

        response.setUpdatedAt(task.getUpdatedAt());

        return response;
    }
}