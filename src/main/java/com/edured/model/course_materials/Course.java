package com.edured.model.course_materials;


import com.edured.model.users.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String courseName;
    private String dateOfCreation;
    private String lastUpdatedDate;
    private int rating;
    @Column(unique = true)
    private String slug;
    // @JsonBackReference
    // @ManyToMany(fetch = FetchType.LAZY)
    // private Set<Student> students;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                ", lastUpdatedDate='" + lastUpdatedDate + '\'' +
                ", rating=" + rating +
                ", slug='" + slug + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}

