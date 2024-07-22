package com.example.calendarize.dto;


import com.example.calendarize.constant.TaskStatusConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    protected String name;

    protected String description;

    protected LocalDateTime startDate;

    protected LocalDateTime endDate;

    protected String status = TaskStatusConstant.DEFAULT_STATUS;
}