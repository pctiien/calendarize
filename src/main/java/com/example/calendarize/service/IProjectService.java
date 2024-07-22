package com.example.calendarize.service;

import com.example.calendarize.dto.ProjectDto;

import java.util.List;

public interface IProjectService {
    List<ProjectDto> getProjectsByUser(Long userId);
    ProjectDto createProjects(ProjectDto dto);
    void addMemberToProject(Long projectId, Long userId);
}
