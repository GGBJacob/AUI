## Laboratory 6: Docker & Docker Compose

### Objective:
Learn how to containerize applications using Docker and orchestrate them with Docker Compose. This includes working with Spring Boot and Angular applications alongside external databases.

### Instructions:
1. **Docker Container for Angular Application**
   - Create a Docker container based on the NGINX image.
   - Build the Angular application and configure NGINX as a proxy using environment variables.
   - Expose the appropriate port for the container.  

2. **Docker Container for Spring Boot Categories Application**
   - Create a Docker container based on the Eclipse Temurin image.
   - Configure the Spring Boot application using environment variables.
   - Expose the appropriate port for the container.  

3. **Docker Container for Spring Boot Elements Application**
   - Create a Docker container based on the Eclipse Temurin image.
   - Configure the Spring Boot application using environment variables.
   - Expose the appropriate port for the container.  

4. **Docker Compose Configuration**
   - Create a `docker-compose.yml` file to include all three applications:
     - Angular application
     - Spring Boot categories application
     - Spring Boot elements application
   - Expose ports for each service and configure containers with the necessary environment variables.  

5. **External Database for Categories**
   - Add an external database container (choose any database) to the Docker Compose configuration.
   - Configure the Spring Boot categories application to use this database container.  

6. **External Database for Elements**
   - Add an external database container (choose any database) to the Docker Compose configuration.
   - Configure the Spring Boot elements application to use this database container.  


