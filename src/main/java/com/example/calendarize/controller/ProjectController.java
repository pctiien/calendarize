package com.example.calendarize.controller;

import com.example.calendarize.dto.ProjectDto;
import com.example.calendarize.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final IProjectService projectService;

    @Autowired
    public ProjectController(IProjectService _projectService)
    {
        this.projectService = _projectService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getProjectByUser(@PathVariable Long userId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectsByUser(userId));
    }
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProjects(dto));
    }

    @PostMapping("/{projectId}/members/{memberId}")
    public ResponseEntity<Void> addMemberToProject(@PathVariable Long projectId,@PathVariable Long memberId)
    {
        projectService.addMemberToProject(projectId,memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/{projectId}/members")
    public ResponseEntity<Void> addMemberToProject(@PathVariable Long projectId,@RequestParam String email)
    {
        projectService.addMemberToProjectByEmail(projectId,email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
