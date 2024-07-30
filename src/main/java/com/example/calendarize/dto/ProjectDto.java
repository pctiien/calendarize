package com.example.calendarize.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto extends TaskDto {
    private Long hostId;
    private Set<UserDto> users ;
}
