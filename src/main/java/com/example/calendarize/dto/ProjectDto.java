package com.example.calendarize.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto extends TaskDto {
    private Long hostId;
}
