# Laboratory 7: Advanced Deployment with Docker Compose

## Objective:
Learn advanced deployment techniques for web services, including configuring environments, service discovery, centralized configurations, and load balancing. Implement and test these features using Docker Compose.

## Instructions:

### 1. Service Registration with Discovery Service
- Configure both Spring Boot applications (`categories` and `elements`) to register with a single discovery service.
- Deploy **two instances** of the `elements` service.
- Verify service registration using logs or a discovery service dashboard.

### 2. Load Balancing with Discovery Service
- Configure a gateway service and ensure it communicates with other services (`categories` and `elements`) using the discovery service.
- Enable a load balancer to distribute requests across multiple instances of the `elements` service.
- Use logs to demonstrate requests being routed to different instances.

### 3. External Database with Volume Configuration
- Use an external database server of your choice for both the `categories` and `elements` services.
- Configure persistent storage for the database using Docker volumes.
- Ensure multiple instances of a service (e.g., `elements`) connect to the same database instance.

### 4. Automatic Database Table Creation
- Implement automatic database table creation using a migration framework (e.g., Flyway or Liquibase).
- Verify that database tables are created on application startup.

### 5. Centralized Configuration Service
- Use a centralized configuration service to provide configuration data for all services, including database and discovery service settings.
- Ensure the location of the configuration service and instance IDs are set using environment variables.

## Service Ports

Below are the port mappings for the different services in the application:

- **Discovery Service**: `8761`
- **Server Service**: `8888`
- **Brands Service**: `8081`
- **Cars Service 1**: `8082`
- **Cars Service 2**: `8083`
- **Gateway Service**: `8080`
- **Angular Frontend**: `8084`