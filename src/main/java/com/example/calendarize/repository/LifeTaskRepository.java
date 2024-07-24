package com.example.calendarize.repository;

import com.example.calendarize.entity.LifeTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LifeTaskRepository extends JpaRepository<LifeTask,Long> {
    Optional<List<LifeTask>> findAllByUserId(Long userId);
}
