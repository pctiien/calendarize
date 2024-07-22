package com.example.calendarize.repository;

import com.example.calendarize.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStatusRepository  extends JpaRepository<TaskStatus,Integer> {
    Optional<TaskStatus> findByName(String name);
}
