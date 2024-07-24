package com.example.calendarize.service.impl;

import com.example.calendarize.constant.TaskStatusConstant;
import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.dto.ProjectTaskDto;
import com.example.calendarize.dto.TaskDto;
import com.example.calendarize.entity.*;
import com.example.calendarize.exception.ResourceNotFoundException;
import com.example.calendarize.mapper.LifeTaskMapper;
import com.example.calendarize.mapper.ProjectTaskMapper;
import com.example.calendarize.repository.*;
import com.example.calendarize.service.IProjectTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectTaskService implements IProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;

    private final ProjectTaskMapper projectTaskMapper;

    private final ProjectRepository projectRepository;

    private final TaskStatusRepository taskStatusRepository;

    @Override
    public TaskDto createProjectTask(ProjectTaskDto dto) {

        ProjectTask projectTask = projectTaskMapper.mapToProjectTask(dto);

        Optional<Project> project = projectRepository.findById(dto.getProjectId());

        if(project.isEmpty()) throw new ResourceNotFoundException("Project","id",dto.getProjectId().toString());

        projectTask.setProject(project.get());

        Optional<TaskStatus> taskStatus = taskStatusRepository.findByName(TaskStatusConstant.DEFAULT_STATUS);

        if(taskStatus.isEmpty()) throw new ResourceNotFoundException("Task_status","name",TaskStatusConstant.DEFAULT_STATUS);

        projectTask.setStatus(taskStatus.get());

        projectTaskRepository.save(projectTask);

        return dto;
    }

    @Override
    public List<TaskDto> getProjectTasks(Long projectId) {
        Optional<List<ProjectTask>> lifeTasks = projectTaskRepository.findAllByProjectId(projectId);
        return projectTaskMapper.mapToDtos(lifeTasks.orElse(new ArrayList<>()));

    }

    @Override
    public TaskDto doneTask(Long taskId) {

        Optional<ProjectTask> projectTask = projectTaskRepository.findById(taskId);

        if(projectTask.isEmpty()) throw new ResourceNotFoundException("Project_task","id",taskId.toString());

        Optional<TaskStatus> taskStatus = taskStatusRepository.findByName(TaskStatusConstant.DONE_STATUS);

        if(taskStatus.isEmpty()) throw new ResourceNotFoundException("Task_status","name",TaskStatusConstant.DONE_STATUS);

        projectTask.get().setStatus(taskStatus.get());

        projectTaskRepository.save(projectTask.get());

        return projectTaskMapper.mapToDtos(projectTask.stream().toList()).get(0);
    }
}
