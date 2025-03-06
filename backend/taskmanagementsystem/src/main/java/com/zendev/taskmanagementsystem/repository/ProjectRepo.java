package com.zendev.taskmanagementsystem.repository;

import com.zendev.taskmanagementsystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Integer> {

    // Find all projects by status (e.g., "active", "completed")
    List<Project> findByStatus(String status);

    // Find project by its name
    Optional<Project> findByName(String name);

    // Find project by its ID (Corrected method)
    Optional<Project> findById(Integer id); // Corrected method
}
