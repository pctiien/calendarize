package com.example.calendarize.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "startDate")
    public Date startDate;

    @Column(name = "endDate")
    public Date endDate;

    @Column(name = "status")
    public int status;

    @OneToOne
    @JoinColumn(name = "hostId",referencedColumnName = "id")
    private AppUser user ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "project",cascade = {
            CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE
    })
    private Set<ProjectTask> tasks;

    @ManyToMany
    @JoinTable(
            name = "Project_Member",
            joinColumns = @JoinColumn(name ="project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> users;
}
