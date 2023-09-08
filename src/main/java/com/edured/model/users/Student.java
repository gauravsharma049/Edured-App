package com.edured.model.users;

import com.edured.model.course_materials.Course;
import com.edured.model.userInfo.AboutUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studentId;
    private String about;
    private String skills;
    private String education;
    private String graduationYear;
    private String badges;
    private String worksAt;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Course> courses;

    @OneToOne(cascade = CascadeType.ALL)
    private EduredUser user;
    @OneToOne(cascade = CascadeType.ALL)
    private AboutUser aboutUser;
}
