## Laboratory 5: Frontend Development with Angular

### Objective:
Develop a modern web application using the Angular framework to interact with the previously created gateway application. The project should include a routing module to manage navigation between views.

### Instructions:
1. **Categories List View**
   - Create a view (component) displaying a list of all categories.
   - Allow users to remove a selected category.
   - Display a single user-friendly value representing each category.  

2. **Add Category View**
   - Implement a form for adding new categories.
   - Make this view accessible from the categories list view.  

3. **Edit Category View**
   - Create a form for editing existing categories.
   - Pre-populate the form with the original values.
   - Access this view from the categories list view, with the category determined by a path parameter.  

4. **Category Details View**
   - Display all details of a selected category along with a list of its elements.
   - Use a single user-friendly value to represent each element.
   - Allow users to remove a selected element.
   - Determine the category using a path parameter.  

5. **Add Element View**
   - Implement a form for adding new elements.
   - Make this view accessible from the category details view.
   - Automatically associate the new element with the category determined by the path parameter. 

6. **Edit Element View**
   - Create a form for editing existing elements.
   - Pre-populate the form with the original values.
   - Access this view from the category details view, with the category and element determined by path parameters.  

7. **Element Details View**
   - Display all details of a selected element.
   - Use path parameters to determine the category and element.
