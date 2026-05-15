package com.task.taskflow_api.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.taskflow_api.dto.request.LoginRequest;
import com.task.taskflow_api.dto.request.RegisterRequest;
import com.task.taskflow_api.dto.response.AuthResponse;
import com.task.taskflow_api.entity.User;
import com.task.taskflow_api.enums.Role;
import com.task.taskflow_api.repository.UserRepository;
import com.task.taskflow_api.security.JwtUtil;
import com.task.taskflow_api.service.interfaces.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {

             // Prevent duplicate user registration
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Default role assigned during registration
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        // Authenticate user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        // Generate JWT token after successful login
        String token = jwtUtil.generateToken(request.getEmail());

        return new AuthResponse(token);
    }
}