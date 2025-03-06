package com.zendev.taskmanagementsystem.repository;

import com.zendev.taskmanagementsystem.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<CustomUser, Integer> {

    Optional<CustomUser> findByEmail(String email);
}
