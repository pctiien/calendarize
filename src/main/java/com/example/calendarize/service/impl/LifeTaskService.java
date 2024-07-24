package com.example.calendarize.service.impl;

import com.example.calendarize.constant.TaskStatusConstant;
import com.example.calendarize.dto.LifeTaskDto;
import com.example.calendarize.dto.TaskDto;
import com.example.calendarize.entity.AppUser;
import com.example.calendarize.entity.LifeTask;
import com.example.calendarize.entity.TaskStatus;
import com.example.calendarize.exception.ResourceNotFoundException;
import com.example.calendarize.mapper.LifeTaskMapper;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.repository.LifeTaskRepository;
import com.example.calendarize.repository.TaskStatusRepository;
import com.example.calendarize.service.ILifeTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LifeTaskService implements ILifeTaskService {

    private final LifeTaskRepository lifeTaskRepository;

    private final LifeTaskMapper lifeTaskMapper;

    private final AppUserRepository appUserRepository;

    private final TaskStatusRepository taskStatusRepository;

    @Override
    public TaskDto addLifeTask(LifeTaskDto dto) {

        LifeTask lifeTask = lifeTaskMapper.mapToLifeTask(dto);

        Optional<AppUser> user = appUserRepository.findById(dto.getUserId());

        if(user.isEmpty()) throw new ResourceNotFoundException("User","id",dto.getUserId().toString());

        lifeTask.setUser(user.get());

        Optional<TaskStatus> taskStatus = taskStatusRepository.findByName(TaskStatusConstant.DEFAULT_STATUS);

        if(taskStatus.isEmpty()) throw new ResourceNotFoundException("Task_status","name",TaskStatusConstant.DEFAULT_STATUS);

        lifeTask.setStatus(taskStatus.get());

        lifeTaskRepository.save(lifeTask);

        return dto;
    }

    @Override
    public List<TaskDto> getLifeTasks(Long userId) {
        Optional<List<LifeTask>> lifeTasks = lifeTaskRepository.findAllByUserId(userId);
        return lifeTaskMapper.mapToDtos(lifeTasks.orElse(new ArrayList<>()));

    }

    @Override
    public TaskDto doneTask(Long taskId) {

        Optional<LifeTask> lifeTask = lifeTaskRepository.findById(taskId);

        if(lifeTask.isEmpty()) throw new ResourceNotFoundException("Life_task","id",taskId.toString());

        Optional<TaskStatus> taskStatus = taskStatusRepository.findByName(TaskStatusConstant.DONE_STATUS);

        if(taskStatus.isEmpty()) throw new ResourceNotFoundException("Task_status","name",TaskStatusConstant.DONE_STATUS);

        lifeTask.get().setStatus(taskStatus.get());

        lifeTaskRepository.save(lifeTask.get());

        return lifeTaskMapper.mapToDtos(lifeTask.stream().toList()).get(0);
    }
}
