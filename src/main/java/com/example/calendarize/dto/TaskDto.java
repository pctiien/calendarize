package com.example.calendarize.dto;


import com.example.calendarize.constant.TaskStatusConstant;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaskDto {
    protected Long id;

    protected String name;

    protected String description;

    protected LocalDateTime startDate;

    protected LocalDateTime endDate;

    protected String status = TaskStatusConstant.DEFAULT_STATUS;

}