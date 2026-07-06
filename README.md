# TaskFlow API

A production-grade Task Management REST API built with **Java 21** and **Spring Boot**, demonstrating enterprise backend engineering practices including JWT authentication, layered architecture, DTO-based API design, validation, exception handling, database migrations with Flyway, and comprehensive unit testing.

Built as a case study solution for a Full Stack Java Developer role requiring RESTful API design, authentication, and clean code practices.

---

## Features

### Authentication & Security
- User Registration & Login
- **JWT-based Stateless Authentication**
- **BCrypt Password Encryption**
- Spring Security with protected endpoints
- **User-specific task ownership** (users can only access their own tasks)

### Task Management
- Create, Read, Update, Delete tasks
- **Mark task as complete** (PATCH endpoint)
- **Update task status** (pending → in_progress → completed)
- Due date tracking with validation

### Engineering Best Practices
- **DTO-based API design** — internal entities never exposed
- **Jakarta Validation** — request-level input validation
- **Global Exception Handling** — structured error responses with proper HTTP status codes
- **Flyway Database Migrations** — version-controlled schema evolution
- **Layered Architecture** — Controller → DTO → Service → Entity → Repository
- **JUnit 5 + Mockito** — unit tests for critical business logic

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Modern LTS with pattern matching, records, virtual threads ready |
| Spring Boot 3.x | Rapid application development with auto-configuration |
| Spring Security + JWT | Stateless authentication & authorization |
| Spring Data JPA | ORM with repository pattern |
| PostgreSQL | Production-grade relational database |
| Flyway | Database versioning & migration |
| Maven | Build & dependency management |
| JUnit 5 + Mockito | Unit testing with mocking |

---

## API Design

### Authentication
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | Login, receive JWT |
| POST | `/api/v1/tasks` | Create task (protected) |
| GET | `/api/v1/tasks` | Get all user tasks (protected) |
| GET | `/api/v1/tasks/{id}` | Get task by ID (protected) |
| PUT | `/api/v1/tasks/{id}` | Update task (protected) |
| DELETE | `/api/v1/tasks/{id}` | Delete task (protected) |
| PATCH | `/api/v1/tasks/{id}/complete` | Mark complete (protected) |
| PATCH | `/api/v1/tasks/{id}/status` | Update status (protected) |

### Request/Response Example
``json
// POST /api/v1/tasks
{
  "title": "Implement JWT Authentication",
  "description": "Add Spring Security with JWT tokens",
  "dueDate": "2026-07-15T18:00:00"
}

// Response 201 Created
{
  "id": 1,
  "title": "Implement JWT Authentication",
  "description": "Add Spring Security with JWT tokens",
  "dueDate": "2026-07-15T18:00:00",
  "status": "PENDING",
  "createdAt": "2026-07-01T10:30:00",
  "updatedAt": "2026-07-01T10:30:00"
}
### Architecture

┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  Controller │────→│     DTO     │────→│   Service   │────→│  Repository │
│   (REST)    │←────│  (Request/  │←────│  (Business) │←────│    (JPA)    │
└─────────────┘     │  Response)  │     └─────────────┘     └──────┬──────┘
                    └─────────────┘                                │
                                                                   ↓
                                                            ┌─────────────┐
                                                            │  PostgreSQL │
                                                            │   (Flyway)  │
                                                            └─────────────┘
### Security Implementation
Stateless JWT Authentication — no server-side session storage
BCrypt Password Hashing — industry-standard password encryption
Token Validation Filter — every protected request validated
User-scoped Queries — tasks filtered by authenticated user ID
### Database Schema
## Users Table
Table
Column	Type	Constraints
id	BIGINT	PK, Auto-increment
name	VARCHAR(100)	Not null
email	VARCHAR(255)	Unique, Not null
password	VARCHAR(255)	Not null (BCrypt hashed)
created_at	TIMESTAMP	Default now()
## Tasks Table
Table
Column	Type	Constraints
id	BIGINT	PK, Auto-increment
title	VARCHAR(200)	Not null
description	TEXT	
due_date	TIMESTAMP	
status	VARCHAR(20)	Default 'PENDING'
created_at	TIMESTAMP	Default now()
updated_at	TIMESTAMP	Auto-update
user_id	BIGINT	FK → users.id
### Getting Started
## Prerequisites
Java 21
Maven 3.9+
PostgreSQL 15+
## Setup
bash
# Clone
git clone https://github.com/QudsiaSiddiqui/TaskFlow.git
cd TaskFlow

# Create database
createdb taskflow_db

# Configure (edit src/main/resources/application.properties)
spring.datasource.url=jdbc:postgresql://localhost:5432/taskflow_db
spring.datasource.username=postgres
spring.datasource.password=your_password

# Run
mvn spring-boot:run

# Test
mvn test
### Testing Strategy
Table
Layer	Coverage	Tools
Service	Business logic, edge cases	JUnit 5, Mockito
Security	Authentication flow	Spring Security Test
Tests validate business logic independently from database and framework dependencies.
### Design Decisions
Table
### Decision	Rationale
DTOs over Entities	Prevents API contract coupling to internal schema; enables flexible versioning
JWT over Sessions	Stateless, scalable across distributed systems; no server-side storage
Flyway over manual DDL	Version-controlled migrations; reproducible environments; team collaboration
Layered Architecture	Separation of concerns; testable units; maintainable codebase
### Future Enhancements
[ ] OpenAPI/Swagger documentation
[ ] Pagination & sorting for task lists
[ ] Role-based access control (ADMIN, USER)
[ ] Refresh token rotation
[ ] Docker Compose for one-command setup
[ ] CI/CD with GitHub Actions
[ ] Integration tests with Testcontainers
[ ] Redis caching for frequent queries
[ ] Rate limiting per user
### Author
# Qudsia Siddiqui
Backend Developer | Java & Spring Boot
GitHub | LinkedIn
