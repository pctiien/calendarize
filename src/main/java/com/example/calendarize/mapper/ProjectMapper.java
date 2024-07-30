package com.example.calendarize.mapper;

import com.example.calendarize.dto.ProjectDto;
import com.example.calendarize.dto.UserDto;
import com.example.calendarize.entity.Project;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    private final AppUserRepository appUserRepository;
    private final TaskStatusRepository taskStatusRepository;


    @Autowired
    public ProjectMapper(AppUserRepository _appUserRepository,TaskStatusRepository _taskStatusRepository)
    {
        appUserRepository = _appUserRepository;
        taskStatusRepository = _taskStatusRepository;
    }

    public List<ProjectDto> mapToDto(List<Project> projects)
    {
        return projects.stream().map(
              project -> ProjectDto.builder()
                      .id(project.getId())
                      .name(project.getName())
                      .description(project.getDescription())
                      .startDate(project.getStartDate())
                      .endDate(project.getEndDate())
                      .hostId(project.getUser().getId())
                      .status(project.getTaskStatus().getName())
                      .users(project.getUsers().stream().map(
                              user->UserDto.builder().email(user.getEmail()).name(user.getName()).build()
                      ).collect(Collectors.toSet()))
                      .build()
        ).collect(Collectors.toList());
    }
    public Project mapToProject(ProjectDto dto){
        return Project.builder().description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .name(dto.getName())
                .user(appUserRepository.findById(dto.getHostId()).get())
                .taskStatus(taskStatusRepository.findByName(dto.getStatus()).get())
                .build();
    }


}
