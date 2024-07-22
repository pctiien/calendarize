package com.example.calendarize.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "App_User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AppUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "pwd")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH
            }
    )
    private Set<LifeTask> tasks ;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "Project_Member",
            joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = {})
    @JoinTable(
            name = "User_Authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;

    public void addProject(Project project){
        if(projects==null)
        {
            projects = new HashSet<>();
        }
        projects.add(project);
    }


}
