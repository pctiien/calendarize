package com.example.calendarize.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Project")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId",referencedColumnName = "id")
    private AppUser user ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "project",cascade = {
            CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE
    })
    private Set<ProjectTask> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Project_Member",
            joinColumns = @JoinColumn(name ="project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> users;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {})
    @JoinColumn(name = "status")
    private TaskStatus taskStatus;

    public void addMember(AppUser member)
    {
        if(users== null)
        {
            users = new HashSet<>();
        }
        users.add(member);
    }


}
