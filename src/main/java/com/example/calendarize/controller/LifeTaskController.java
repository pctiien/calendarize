package com.example.calendarize.controller;

import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.service.ILifeTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/life/task")
public class LifeTaskController {

    private final ILifeTaskService lifeTaskService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getLifeTasks(@PathVariable Long userId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(lifeTaskService.getLifeTasks(userId));
    }

    @PostMapping
    public ResponseEntity<?> addLifeTask(@RequestBody LifeTaskDto dto)
    {
        return ResponseEntity.ok(lifeTaskService.addLifeTask(dto));
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<?> doneTask(@PathVariable Long taskId)
    {
        return ResponseEntity.ok(lifeTaskService.doneTask(taskId));
    }



}
