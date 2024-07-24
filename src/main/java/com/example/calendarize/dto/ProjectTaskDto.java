package com.example.calendarize.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskDto extends TaskDto{
    private Long projectId;
}
