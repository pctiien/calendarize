package com.example.calendarize.service;

import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.dto.TaskDto;
import com.example.calendarize.entity.LifeTask;

import java.util.List;

public interface ILifeTaskService {
    TaskDto addLifeTask(LifeTaskDto dto);
    List<TaskDto> getLifeTasks(Long userId);
    TaskDto doneTask(Long taskId);


}
