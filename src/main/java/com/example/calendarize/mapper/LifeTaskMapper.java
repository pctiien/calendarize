package com.example.calendarize.mapper;

import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.dto.TaskDto;
import com.example.calendarize.entity.LifeTask;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LifeTaskMapper {


    public LifeTask mapToLifeTask(LifeTaskDto dto)
    {
        return LifeTask.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .build();
    }
    public List<TaskDto> mapToDtos(List<LifeTask> lifeTasks){
        return lifeTasks.stream().map(
                lifeTask -> TaskDto.builder().name(lifeTask.getName())
                        .id(lifeTask.getId())
                        .endDate(lifeTask.getEndDate())
                        .startDate(lifeTask.getStartDate())
                        .description(lifeTask.getDescription())
                        .status(lifeTask.getStatus().getName())
                        .build()
        ).collect(Collectors.toList());
    }


}
