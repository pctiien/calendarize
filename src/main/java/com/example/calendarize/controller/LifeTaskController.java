package com.example.calendarize.controller;

import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.dto.TaskDto;
import com.example.calendarize.service.ILifeTaskService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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

    @GetMapping
    public ResponseEntity<?> getTaskBetweenDates(@RequestParam Long userId,
                                                 @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end)
    {
        LocalDateTime startDate = start.atStartOfDay();
        LocalDateTime endDate = end.atTime(LocalTime.MAX);
        return ResponseEntity.ok().body(lifeTaskService.getLifeTaskBetweenDates(userId,startDate,endDate));
    }
    @PutMapping
    public ResponseEntity<?> updateTask(@RequestBody TaskDto dto)
    {
        return ResponseEntity.ok().body(lifeTaskService.updateTask(dto));
    }

}
