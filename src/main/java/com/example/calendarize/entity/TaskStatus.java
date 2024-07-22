package com.example.calendarize.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Task_Status")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatus {
    @Id
    @Column(name ="task_status_id")
    private int task_status_id;

    @Column(name ="name")
    private String name ;

}
