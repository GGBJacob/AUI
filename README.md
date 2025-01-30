# Car Catalog

## Project Description
A modular web application with microservices and containerization. The application consists of Spring Boot microservices, an Angular frontend, and a database system. It implements REST API, load balancing, service registration, centralized configuration, and containerization with Docker Compose.

## Technologies
- **Backend**: Spring Boot (Java)
- **Frontend**: Angular
- **Database**: PostgreSQL / In-memory H2
- **Orchestration**: Docker Compose
- **Configuration**: Spring Cloud Config
- **Service Registration**: Eureka Discovery Service
- **Load Balancing**: Spring Cloud Gateway

## Microservices Structure
- **Discovery Service** (`port: 8761`) – Service registration
- **Configuration Server** (`port: 8888`) – Centralized configuration
- **Brands Service** (`port: 8081`) – Vehicle brand management
- **Cars Service** (`port: 8082, 8083`) – Vehicle information handling
- **Gateway Service** (`port: 8080`) – API routing
- **Frontend (Angular)** (`port: 8084`) – User interface

## Deployment

To launch the latest version, navigate to the Lab7 folder and run the command `docker compose up` in your terminal.

## Author
Project developed as part of laboratory exercises on microservices and containerization by Jakub Romanowski.