package com.example.calendarize.controller;

import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.dto.ProjectTaskDto;
import com.example.calendarize.service.ILifeTaskService;
import com.example.calendarize.service.IProjectTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project/task")
public class ProjectTaskController {

    private final IProjectTaskService projectTaskService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProjectTasks(@PathVariable Long userId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(projectTaskService.getProjectTasks(userId));
    }

    @PostMapping
    public ResponseEntity<?> addProjectTask(@RequestBody ProjectTaskDto dto)
    {
        return ResponseEntity.ok(projectTaskService.createProjectTask(dto));
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<?> doneTask(@PathVariable Long taskId)
    {
        return ResponseEntity.ok(projectTaskService.doneTask(taskId));
    }



}