package com.example.calendarize.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Life_Task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LifeTask extends Task{
    @ManyToOne(fetch = FetchType.LAZY,cascade = {})
    @JoinColumn(name = "user_id")
    private AppUser user;
}
