package com.task.taskflow_api.service.interfaces;

import com.task.taskflow_api.dto.request.LoginRequest;
import com.task.taskflow_api.dto.request.RegisterRequest;
import com.task.taskflow_api.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}