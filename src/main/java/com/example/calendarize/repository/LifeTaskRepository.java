package com.example.calendarize.repository;

import com.example.calendarize.entity.LifeTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LifeTaskRepository extends JpaRepository<LifeTask,Long> {
    Optional<List<LifeTask>> findAllByUserId(Long userId);

    @Query(
            "select lt from LifeTask lt join fetch lt.user where lt.user.id = :userId and lt.startDate between :start and :end"
    )
    Optional<List<LifeTask>> findAllByUserIdBetweenDate(@Param("userId") Long userId,
                                                        @Param("start") LocalDateTime start,
                                                        @Param("end") LocalDateTime end);
}
