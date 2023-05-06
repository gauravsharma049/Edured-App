package com.edured.model.course_materials;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Column(unique = true)
    private String slug;
    private String dateOfCreation;
    private String lastUpdatedDate;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    // @JsonIgnore
    // // @JsonBackReference
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Topic> topics;


    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                ", lastUpdatedDate='" + lastUpdatedDate + '\'' +
                ", course=" + course +
                '}';
    }
}
