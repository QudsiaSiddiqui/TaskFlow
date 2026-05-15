package com.task.taskflow_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.taskflow_api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}