## Laboratory 4: Microservices Architecture

### Objective:
Learn the principles of decomposing a monolithic application into stand-alone modules based on the microservices architecture. Build on the project from previous laboratories.

### Instructions:
1. **Decompose into Separate Applications**
   - Split the original Spring Boot project into two independent applications:
     - **Category Management Application**: Handles all category-related operations.
     - **Elements Management Application**: Handles element-related operations and includes a simplified category management mechanism to maintain relationships.
   - Both applications should use private in-memory H2 databases.

2. **Implement Event-Based Communication**
   - Enable inter-application communication using REST-based event notifications:
     - When a category is added in the **Category Management Application**, notify the **Elements Management Application** to create a corresponding simplified category record.
     - When a category is removed in the **Category Management Application**, notify the **Elements Management Application** to remove related elements.

3. **Create a Gateway Application**
   - Develop a new Spring Boot application using **Spring Cloud Gateway**:
     - Define routing rules for the **Category Management** and **Elements Management** applications.
   - Update and test existing HTTP requests from the previous laboratory to ensure they function with the gateway (update requests to include the gateway port).
