package com.zendev.taskmanagementsystem.service;

import com.zendev.taskmanagementsystem.dto.ReqRes;
import com.zendev.taskmanagementsystem.entity.Project;
import com.zendev.taskmanagementsystem.repository.ProjectRepo;
import com.zendev.taskmanagementsystem.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectManagementService {

    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private TaskRepo taskRepo;

    public ReqRes createProject(Project project) {
        ReqRes response = new ReqRes();
        try {
            Project savedProject = projectRepo.save(project);
            response.setStatusCode(200);
            response.setMessage("Project created successfully");
            response.setProject(savedProject);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating project: " + e.getMessage());
        }
        return response;
    }

    public ReqRes getProjectById(Integer projectId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Project> project = projectRepo.findById(projectId);
            if (project.isPresent()) {
                response.setStatusCode(200);
                response.setMessage("Project found");
                response.setProject(project.get());
            } else {
                response.setStatusCode(404);
                response.setMessage("Project not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving project: " + e.getMessage());
        }
        return response;
    }

    public ReqRes getAllProjects() {
        ReqRes response = new ReqRes();
        try {
            List<Project> projects = projectRepo.findAll();
            if (!projects.isEmpty()) {
                response.setStatusCode(200);
                response.setMessage("Projects retrieved successfully");
                response.setProjectList(projects);
            } else {
                response.setStatusCode(404);
                response.setMessage("No projects found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving projects: " + e.getMessage());
        }
        return response;
    }

    public ReqRes updateProject(Integer projectId, Project updatedProject) {
        ReqRes response = new ReqRes();
        try {
            Optional<Project> projectOptional = projectRepo.findById(projectId);
            if (projectOptional.isPresent()) {
                Project existingProject = projectOptional.get();
                existingProject.setName(updatedProject.getName());
                existingProject.setDescription(updatedProject.getDescription());
                existingProject.setDueDate(updatedProject.getDueDate());

                Project savedProject = projectRepo.save(existingProject);
                response.setStatusCode(200);
                response.setMessage("Project updated successfully");
                response.setProject(savedProject);
            } else {
                response.setStatusCode(404);
                response.setMessage("Project not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating project: " + e.getMessage());
        }
        return response;
    }

    public ReqRes deleteProject(Integer projectId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Project> project = projectRepo.findById(projectId);
            if (project.isPresent()) {
                projectRepo.delete(project.get());
                response.setStatusCode(200);
                response.setMessage("Project deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Project not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting project: " + e.getMessage());
        }
        return response;
    }
}
