package com.zendev.taskmanagementsystem.controller;

import com.zendev.taskmanagementsystem.dto.ReqRes;
import com.zendev.taskmanagementsystem.entity.CustomUser;
import com.zendev.taskmanagementsystem.entity.Task;
import com.zendev.taskmanagementsystem.entity.Project;
import com.zendev.taskmanagementsystem.service.CustomUserManagementService;
import com.zendev.taskmanagementsystem.service.TaskManagementService;
import com.zendev.taskmanagementsystem.service.ProjectManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserManagementController {

    @Autowired
    private CustomUserManagementService usersManagementService;

    @Autowired
    private TaskManagementService taskManagementService;  // Task management service

    @Autowired
    private ProjectManagementService projectManagementService;  // Project management service

    // User-related endpoints
    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg) {
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers() {
        return ResponseEntity.ok(usersManagementService.getAllUsers());
    }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));
    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody CustomUser reqres) {
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }

    // Task-related endpoints
    @PostMapping("/tasks")
    public ResponseEntity<ReqRes> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskManagementService.createTask(task));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<ReqRes> getTaskById(@PathVariable Integer taskId) {
        return ResponseEntity.ok(taskManagementService.getTaskById(taskId));
    }

    @GetMapping("/tasks")
    public ResponseEntity<ReqRes> getAllTasks() {
        return ResponseEntity.ok(taskManagementService.getAllTasks());
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<ReqRes> updateTask(@PathVariable Integer taskId, @RequestBody Task updatedTask) {
        return ResponseEntity.ok(taskManagementService.updateTask(taskId, updatedTask));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<ReqRes> deleteTask(@PathVariable Integer taskId) {
        return ResponseEntity.ok(taskManagementService.deleteTask(taskId));
    }

    // Project-related endpoints
    @PostMapping("/projects")
    public ResponseEntity<ReqRes> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectManagementService.createProject(project));
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ReqRes> getProjectById(@PathVariable Integer projectId) {
        return ResponseEntity.ok(projectManagementService.getProjectById(projectId));
    }

    @GetMapping("/projects")
    public ResponseEntity<ReqRes> getAllProjects() {
        return ResponseEntity.ok(projectManagementService.getAllProjects());
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ReqRes> updateProject(@PathVariable Integer projectId, @RequestBody Project updatedProject) {
        return ResponseEntity.ok(projectManagementService.updateProject(projectId, updatedProject));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<ReqRes> deleteProject(@PathVariable Integer projectId) {
        return ResponseEntity.ok(projectManagementService.deleteProject(projectId));
    }
}
