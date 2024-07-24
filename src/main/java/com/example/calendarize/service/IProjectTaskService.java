package com.example.calendarize.service;


import com.example.calendarize.dto.ProjectTaskDto;
import com.example.calendarize.dto.TaskDto;

import java.util.List;

public interface IProjectTaskService {
    public List<TaskDto> getProjectTasks(Long projectId);
    public TaskDto createProjectTask(ProjectTaskDto dto);
    public TaskDto doneTask(Long projectTaskId);
}
