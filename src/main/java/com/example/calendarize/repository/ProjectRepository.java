package com.example.calendarize.repository;

import com.example.calendarize.entity.AppUser;
import com.example.calendarize.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    public Optional<List<Project>> findAllByUser(AppUser user);
}
