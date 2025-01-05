## Laboratory 1: Introduction and Data Modeling with Java SE

### Objective:
Develop the foundation for an application of your chosen or assigned topic. The project involves creating entity classes with a 1:N relationship and exploring Java SE concepts, Stream API, and serialization.

### Instructions:
1. **Define Entity Classes**
   - Create two business classes (e.g., `Category` and `Element`) with a 1:N relationship.
   - Implement comparison mechanisms (`hashCode`, `equals`, and natural ordering).
   - Use the builder pattern for object creation.
   - Implement a DTO class for the "N" entity to include a field representing its category.

2. **Initialize Application Data**
   - Populate collections of categories and elements in code (no user input).
   - Use nested lambdas to print categories and their elements in the original order.

3. **Stream API Tasks**
   - Create a `Set` of all elements across categories and print it.
   - Filter, sort, and print elements by specific properties using a single Stream pipeline.
   - Transform elements into DTO objects, sort them naturally, collect into a `List`, and print.

4. **Serialization**
   - Save the category collection to a binary file.
   - Read the data back and print the categories with elements.

5. **Parallel Processing**
   - Use custom thread pools with Stream API parallel pipelines.
   - Simulate workload with tasks like printing elements with delays (`Thread.sleep()`).
   - Experiment with different thread pool sizes (e.g., `ForkJoinPool`) and observe results.
