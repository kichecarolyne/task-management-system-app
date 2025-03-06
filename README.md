Task Management System

A simple Task Management System that allows users to create, update, and delete tasks.


Features

User Authentication:

Users can register and log in securely using JWT-based authentication.

Task Management:

Users can perform CRUD (Create, Read, Update, Delete) operations on tasks:

Create a Task: Add a task with a title, description, status, and due date.

Update a Task: Modify task details such as title, description, status, or due date.

Delete a Task: Remove a task.

Get Tasks: List all tasks for the logged-in user.


Admin Role:

Admins can create, read, update, and delete projects.

Admins can assign tasks to users (linked to projects).

Admins can view all projects and tasks created by them.

Admins can view all users and delete their accounts.

User Role:

Users can view, edit, and delete their own tasks (tasks that they created and are not linked to any project).

Users can view and update tasks assigned to them by Admin (tasks linked to projects).

Users cannot create or delete projects or assign tasks to others.


Tech Stack

Backend

Java Spring Boot

A powerful framework to build Java-based web applications.

Spring Security

Provides security features like JWT-based authentication.

Hibernate/JPA

ORM framework for interacting with databases.

MySQL

Relational database for storing user and task data.

Maven or Gradle

Build tools for managing dependencies and project build lifecycle.

RESTful APIs

RESTful API endpoints for task management operations.

Frontend

React

JavaScript library for building user interfaces.

React Router

For handling navigation in the app.

Axios

To make API calls from the frontend to the backend.

Material UI

For styling the application.

Setup & Installation

Backend Setup

Clone the repository:

git clone https://github.com/username/task-management-system-app.git

cd task-management-system

Configure your database connection in the application.properties or application.yml file for Spring Boot:

spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb

spring.datasource.username=your-username

spring.datasource.password=your-password

Install dependencies and build the backend:

If using Maven:

mvn clean install

If using Gradle:

./gradlew build

Run the backend:

mvn spring-boot:run   # For Maven

./gradlew bootRun     # For Gradle

Frontend Setup

Navigate to the frontend directory:

cd frontend

Install dependencies:

npm install

Run the frontend:

npm start

Open your browser and visit http://localhost:3000 to use the app.

API Endpoints

POST /api/auth/register

Register a new user with a username, email, and password.

POST /api/auth/login

Log in with email and password. Returns a JWT token.

GET /api/tasks

Get all tasks for the authenticated user.

POST /api/tasks

Create a new task with title, description, status, and due date.

PUT /api/tasks/{taskId}

Update a specific task by task ID.

DELETE /api/tasks/{taskId}

Delete a specific task by task ID.

Authentication

The backend uses JWT (JSON Web Tokens) for authentication. Upon successful login, a token is issued, which must be included in the Authorization header for subsequent API requests.

Technologies Used

Spring Boot for backend development

Spring Security for authentication and authorization

React for building the frontend user interface

MySQL for database management

Material UI (optional) for UI 