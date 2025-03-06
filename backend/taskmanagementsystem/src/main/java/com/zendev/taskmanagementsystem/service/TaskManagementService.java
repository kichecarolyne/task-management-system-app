package com.zendev.taskmanagementsystem.service;

import com.zendev.taskmanagementsystem.dto.ReqRes;
import com.zendev.taskmanagementsystem.entity.Task;
import com.zendev.taskmanagementsystem.entity.CustomUser; // Correct import for CustomUser
import com.zendev.taskmanagementsystem.entity.Project;
import com.zendev.taskmanagementsystem.repository.TaskRepo;
import com.zendev.taskmanagementsystem.repository.UsersRepo;
import com.zendev.taskmanagementsystem.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManagementService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private ProjectRepo projectRepo; // This repo handles project-related tasks

    // Get task by ID
    public ReqRes getTaskById(Integer taskId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Task> task = taskRepo.findById(taskId);
            if (task.isPresent()) {
                response.setStatusCode(200);
                response.setMessage("Task found");
                response.setTask(task.get());
            } else {
                response.setStatusCode(404);
                response.setMessage("Task not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving task: " + e.getMessage());
        }
        return response;
    }

    // Get all tasks
    public ReqRes getAllTasks() {
        ReqRes response = new ReqRes();
        try {
            List<Task> tasks = taskRepo.findAll();
            if (!tasks.isEmpty()) {
                response.setStatusCode(200);
                response.setMessage("Tasks retrieved successfully");
                response.setTaskList(tasks);
            } else {
                response.setStatusCode(404);
                response.setMessage("No tasks found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving tasks: " + e.getMessage());
        }
        return response;
    }

    // Admin creates and assigns task to a user
    public ReqRes createTask(Task task) {
        ReqRes response = new ReqRes();
        try {
            // Check if the user exists
            if (usersRepo.existsById(task.getAssignedTo().getId())) {
                if (task.getDueDate() == null) {
                    response.setStatusCode(400);
                    response.setMessage("Due date is required for the task.");
                    return response;
                }

                // If task is to be linked to a project
                if (task.getProject() != null && !projectRepo.existsById(task.getProject().getId())) {
                    response.setStatusCode(404);
                    response.setMessage("Assigned project not found");
                    return response;
                }

                Task savedTask = taskRepo.save(task);
                response.setStatusCode(200);
                response.setMessage("Task created successfully");
                response.setTask(savedTask);
            } else {
                response.setStatusCode(404);
                response.setMessage("Assigned user not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating task: " + e.getMessage());
        }
        return response;
    }

    // Admin assigns a task to a user by email
    public ReqRes assignTaskToUser(Integer taskId, String email) {
        ReqRes response = new ReqRes();
        try {
            Optional<Task> taskOptional = taskRepo.findById(taskId);
            if (taskOptional.isPresent()) {
                Optional<CustomUser> userOptional = usersRepo.findByEmail(email);
                if (userOptional.isPresent()) {
                    Task task = taskOptional.get();
                    task.setAssignedTo(userOptional.get());
                    taskRepo.save(task);
                    response.setStatusCode(200);
                    response.setMessage("Task assigned successfully to " + email);
                    response.setTask(task);
                } else {
                    response.setStatusCode(404);
                    response.setMessage("User not found with email: " + email);
                }
            } else {
                response.setStatusCode(404);
                response.setMessage("Task not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error assigning task to user: " + e.getMessage());
        }
        return response;
    }

    // Retrieve tasks by project for admin
    public ReqRes getTasksByProject(Integer projectId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Project> projectOptional = projectRepo.findById(projectId);
            if (projectOptional.isPresent()) {
                List<Task> tasks = taskRepo.findByProject(projectOptional.get()); // Ensure this method is implemented in TaskRepo
                response.setStatusCode(200);
                response.setMessage("Tasks retrieved for project");
                response.setTaskList(tasks);
            } else {
                response.setStatusCode(404);
                response.setMessage("Project not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving tasks by project: " + e.getMessage());
        }
        return response;
    }

    // Users can only view and update tasks linked to their assigned project
    public ReqRes getUserTasks(Integer userId) {
        ReqRes response = new ReqRes();
        try {
            Optional<CustomUser> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                List<Task> tasks = taskRepo.findByAssignedTo(userOptional.get()); // Ensure this method is implemented in TaskRepo
                response.setStatusCode(200);
                response.setMessage("Tasks retrieved for user");
                response.setTaskList(tasks);
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving tasks for user: " + e.getMessage());
        }
        return response;
    }

    // Update task
    public ReqRes updateTask(Integer taskId, Task updatedTask) {
        ReqRes response = new ReqRes();
        try {
            Optional<Task> taskOptional = taskRepo.findById(taskId);
            if (taskOptional.isPresent()) {
                Task existingTask = taskOptional.get();
                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setStatus(updatedTask.getStatus());
                existingTask.setAssignedTo(updatedTask.getAssignedTo());
                existingTask.setProject(updatedTask.getProject());
                existingTask.setDueDate(updatedTask.getDueDate());

                Task savedTask = taskRepo.save(existingTask);
                response.setStatusCode(200);
                response.setMessage("Task updated successfully");
                response.setTask(savedTask);
            } else {
                response.setStatusCode(404);
                response.setMessage("Task not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating task: " + e.getMessage());
        }
        return response;
    }

    // Delete task (updated logic)
    public ReqRes deleteTask(Integer taskId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Task> taskOptional = taskRepo.findById(taskId);
            if (taskOptional.isPresent()) {
                Task task = taskOptional.get();
                CustomUser currentUser = getCurrentUser(); // Get the current logged-in user

                // If the current user is an admin, they can delete any task
                if (currentUser.getRole().equalsIgnoreCase("ADMIN")) {
                    taskRepo.delete(task);
                    response.setStatusCode(200);
                    response.setMessage("Task deleted successfully by admin.");
                } else {
                    // If the task is linked to a project, users can't delete it
                    if (task.getProject() != null) {
                        response.setStatusCode(403); // Forbidden
                        response.setMessage("Users cannot delete tasks linked to projects.");
                    } else {
                        // Only allow deletion if the task was created by the current user
                        if (task.getAssignedTo().getId().equals(currentUser.getId())) {
                            taskRepo.delete(task);
                            response.setStatusCode(200);
                            response.setMessage("Task deleted successfully.");
                        } else {
                            response.setStatusCode(403); // Forbidden
                            response.setMessage("Users can only delete tasks they created.");
                        }
                    }
                }
            } else {
                response.setStatusCode(404);
                response.setMessage("Task not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting task: " + e.getMessage());
        }
        return response;
    }

    // Helper method to get current logged-in user
    private CustomUser getCurrentUser() {
        // Retrieve the logged-in user using Spring Security
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usersRepo.findByEmail(principal.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
