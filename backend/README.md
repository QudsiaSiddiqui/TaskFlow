# TaskFlow API

A production-style Task Management REST API built using Java 21 and Spring Boot.

This project demonstrates backend engineering best practices including JWT authentication, layered architecture, DTO-based API design, validation, exception handling, database migrations, and RESTful API development.

---

# Features

## Authentication
- User Registration
- User Login
- JWT-based Authentication
- Password Encryption using BCrypt
- Stateless Security

## Task Management
- Create Task
- Get All Tasks
- Get Task By ID
- Update Task
- Delete Task
- Mark Task as Completed
- Update Task Status

## Validation & Error Handling
- Request Validation using Jakarta Validation
- Global Exception Handling
- Proper HTTP Status Codes
- Structured Error Responses

## Database
- PostgreSQL
- Flyway Database Migrations
- JPA/Hibernate ORM

## Testing
- Unit Testing using JUnit and Mockito

---

# Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Security |
| JWT | Stateless Authentication |
| Spring Data JPA | ORM & Database Access |
| PostgreSQL | Relational Database |
| Flyway | Database Versioning |
| Maven | Dependency Management |
| JUnit & Mockito | Unit Testing |

---

# Project Structure

src/main/java/com/task/taskflow_api

├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exceptions
├── mapper
├── repository
├── security
├── service
│   ├── impl
│   └── interfaces
src/test/java/com/task/taskflow_api
├──service

# Authentication Flow
User registers using /api/v1/auth/register
User logs in using /api/v1/auth/login
Server generates JWT token
Client sends token in Authorization header
Protected APIs validate token before processing requests

Example Authorization Header:

Authorization: Bearer your_jwt_token

# Database     Schema
Users        Table
Column	     Type
id	         BIGINT
name	     VARCHAR
email	     VARCHAR
password	 VARCHAR
created_at	 TIMESTAMP

# Tasks Table
Column	    Type
id	        BIGINT
title	    VARCHAR
description	TEXT
due_date	TIMESTAMP
status	    VARCHAR
created_at	TIMESTAMP
updated_at	TIMESTAMP
user_id	    BIGINT

# API Endpoints
## Authentication APIs
### Register User
POST /api/v1/auth/register

Request Body:

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
Response:

{
  "token": "jwt_token_here"
}
### Login User
POST /api/v1/auth/login

Request Body:
{
  "email": "john@example.com",
  "password": "password123"
}

Response:

{
  "token": "jwt_token_here"
}
## Task APIs
### Create Task
POST /api/v1/tasks

Request Body:

{
  "title": "Finish Backend Assignment",
  "description": "Complete all APIs and security",
  "dueDate": "2026-05-20T18:00:00"
}
### Get All Tasks
### GET /api/v1/tasks
### Get Task By ID
### GET /api/v1/tasks/{id}
### Update Task
PUT /api/v1/tasks/{id}

Request Body:

{
  "title": "Updated Task",
  "description": "Updated description",
  "dueDate": "2026-05-25T20:00:00"
}
### Delete Task
### DELETE /api/v1/tasks/{id}
### Mark Task Complete
### PATCH /api/v1/tasks/{id}/complete
### Update Task Status
### PATCH /api/v1/tasks/{id}/status

Request Body:

{
  "status": "IN_PROGRESS"
}
# Running the Application
## Prerequisites

Make sure the following are installed:

Java 21
Maven
PostgreSQL
# Clone Repository
git clone <repository-url>
# Configure Database

Create PostgreSQL database:

CREATE DATABASE taskflow_db;

Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/taskflow_db
spring.datasource.username=postgres
spring.datasource.password=your_password
# Run Application
mvn spring-boot:run

Application will start at:

http://localhost:8080
# Running Tests
mvn test
# Security Implementation

The application uses Spring Security with JWT authentication.

Key security features include:

Stateless authentication
Password hashing using BCrypt
JWT token validation
Protected task endpoints
User-specific task ownership

Users can only access and manage their own tasks.

# Design Decisions
## Why DTOs?

DTOs prevent exposing internal entity structures and improve API maintainability.

## Why JWT?

JWT enables stateless authentication and improves scalability for distributed systems.

## Why Flyway?

Flyway provides version-controlled database migrations and ensures schema consistency across environments.

## Why Layered Architecture?

Separating controller, service, and repository layers improves maintainability, scalability, and testability.

# Future Improvements

Potential production enhancements:

Swagger/OpenAPI Documentation
Pagination & Sorting
Role-Based Authorization
Refresh Tokens
Docker Compose Setup
CI/CD Pipeline
Integration Testing with Testcontainers
Redis Caching
Rate Limiting
# Testing

Unit tests were implemented for critical service-layer functionality using:

JUnit 5
Mockito

Testing focuses on validating business logic independently from database and framework dependencies.

# Author

Qudsia Siddiqui

Backend Developer | Java & Spring Boot Enthusiast
