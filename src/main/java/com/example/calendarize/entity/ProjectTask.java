package com.example.calendarize.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Project_Task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectTask extends Task{
    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {})
    public Project project;
}
