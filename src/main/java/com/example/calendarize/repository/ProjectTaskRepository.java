package com.example.calendarize.repository;

import com.example.calendarize.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask,Long> {
    Optional<List<ProjectTask>> findAllByProjectId(Long projectId);
}
