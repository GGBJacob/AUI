## Laboratory 3: Spring MVC and REST Services

### Objective:
Learn the basic components of Spring MVC and implement RESTful services for the project built in previous laboratories.

### Instructions:
1. **Create DTO Classes**
   - Implement separate DTO classes for:
     - **Creating/Updating entities**: Include only fields that can be set (e.g., omit primary keys and categories, as these are determined by resource paths).
     - **Reading entities**: Include fields relevant for detailed views of entities.
     - **Reading collections**: Provide only identifiers and user-friendly names/descriptions.
   - Ensure clear separation between DTOs for different use cases.

2. **Develop REST Controllers**
   - Create `@RestController` for each entity class.
   - Utilize services to handle business operations and translate between business entities and DTOs.
   - Implement the following endpoints:
     - **Create/Update**: Add or modify elements and categories.
     - **Delete**: Remove elements and categories (deleting a category should cascade to its elements via JPA configuration).
     - **Read**: Fetch individual entities, all entities, or filtered collections (e.g., all elements or elements within a specific category).
   - Ensure RESTful resource addresses are well-structured and hierarchical.
   - Use appropriate HTTP methods (e.g., `GET`, `POST`, `PUT`, `DELETE`) and response codes (e.g., 200, 201, 404, 400).
   - Handle edge cases, such as:
     - Fetching elements from an empty category vs. a non-existent category (distinct responses).
     - Adding an element to a non-existent category (appropriate error response).

3. **Document and Test Endpoints**
   - Create an `HTTP request` file (`request.http`) documenting all REST endpoints.
   - Include sample requests for each operation.
   - Test the endpoints using tools like IntelliJ IDEA HTTP Client or Visual Studio Code REST Client plugin.
