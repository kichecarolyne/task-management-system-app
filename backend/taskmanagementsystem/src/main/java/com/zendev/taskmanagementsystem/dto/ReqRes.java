package com.zendev.taskmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zendev.taskmanagementsystem.entity.CustomUser;
import com.zendev.taskmanagementsystem.entity.Task;
import com.zendev.taskmanagementsystem.entity.Project;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String role;
    private String email;
    private String password;
    private CustomUser customUsers;
    private List<CustomUser> customUsersList;
    private Task task;
    private List<Task> taskList;
    private Project project;
    private List<Project> projectList;

}
