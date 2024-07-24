package com.example.calendarize.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Life_Task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class LifeTask extends Task{
    @ManyToOne(fetch = FetchType.LAZY,cascade = {})
    @JoinColumn(name = "user_id")
    private AppUser user;
}
