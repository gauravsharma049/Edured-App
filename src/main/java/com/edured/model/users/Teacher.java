package com.edured.model.users;


import com.edured.model.course_materials.Course;
import com.edured.model.userInfo.AboutUser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses;

    @OneToOne(cascade = CascadeType.ALL)
    private EduredUser user;
    @OneToOne(cascade = CascadeType.ALL)
    private AboutUser aboutUser;
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}