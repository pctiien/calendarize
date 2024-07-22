package com.example.calendarize.service.impl;

import com.example.calendarize.dto.ProjectDto;
import com.example.calendarize.entity.AppUser;
import com.example.calendarize.entity.Project;
import com.example.calendarize.exception.ResourceNotFoundException;
import com.example.calendarize.mapper.ProjectMapper;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.repository.ProjectRepository;
import com.example.calendarize.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectMapper projectMapper;
    @Autowired
    public ProjectService(
            ProjectRepository _projectRepository,AppUserRepository _appUserRepository
            ,ProjectMapper _projectMapper
    ){
        this.projectRepository = _projectRepository;
        this.appUserRepository = _appUserRepository;
        this.projectMapper = _projectMapper;
    }

    @Override
    public List<ProjectDto> getProjectsByUser(Long userId) {
        Optional<AppUser> user = appUserRepository.findById(userId);
        if(user.isEmpty()) throw new ResourceNotFoundException("User","id",userId.toString());
        Optional<List<Project>> projects = projectRepository.findAllByUser(user.get());
        return projectMapper.mapToDto(projects.orElse(new ArrayList<>()));
    }

    @Override
    public ProjectDto createProjects(ProjectDto dto) {
        Project project = projectMapper.mapToProject(dto);
        Optional<AppUser> userOptional = appUserRepository.findById(dto.getHostId());
        if(userOptional.isPresent())
        {
            project.addMember(userOptional.get());
        }
        projectRepository.save(project);
        return dto;
    }

    @Override
    public void addMemberToProject(Long projectId, Long userId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()) throw new ResourceNotFoundException("Project","id",projectId.toString());
        Optional<AppUser> member = appUserRepository.findById(userId);
        if(member.isEmpty()) throw new ResourceNotFoundException("User","id",userId.toString());
        project.get().addMember(member.get());
        projectRepository.save(project.get());
    }

}
