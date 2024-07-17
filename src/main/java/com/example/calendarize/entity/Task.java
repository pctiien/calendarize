package com.example.calendarize.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "startDate")
    public LocalDateTime startDate;

    @Column(name = "endDate")
    public LocalDateTime endDate;
}
