package com.example.calendarize.service;

import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.dto.TaskDto;
import com.example.calendarize.entity.LifeTask;

import java.time.LocalDateTime;
import java.util.List;

public interface ILifeTaskService {
    TaskDto addLifeTask(LifeTaskDto dto);
    List<TaskDto> getLifeTasks(Long userId);
    TaskDto doneTask(Long taskId);
    List<List<TaskDto>> getLifeTaskBetweenDates(Long userId, LocalDateTime startDate,LocalDateTime endDate);
    TaskDto updateTask(TaskDto dto);
}
