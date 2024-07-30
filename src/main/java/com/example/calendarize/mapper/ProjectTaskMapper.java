package com.example.calendarize.mapper;

import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.dto.ProjectTaskDto;
import com.example.calendarize.dto.TaskDto;
import com.example.calendarize.entity.LifeTask;
import com.example.calendarize.entity.ProjectTask;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectTaskMapper {
    public ProjectTask mapToProjectTask(ProjectTaskDto dto)
    {
        return ProjectTask.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .build();
    }
    public List<TaskDto> mapToDtos(List<ProjectTask> projectTasks){
        return projectTasks.stream().map(
                projectTask -> TaskDto.builder()
                        .name(projectTask.getName())
                        .id(projectTask.getId())
                        .endDate(projectTask.getEndDate())
                        .startDate(projectTask.getStartDate())
                        .description(projectTask.getDescription())
                        .status(projectTask.getStatus().getName())
                        .build()
        ).collect(Collectors.toList());
    }

}
