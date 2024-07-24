package com.example.calendarize.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Project_Task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProjectTask extends Task{
    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {})
    public Project project;
}
