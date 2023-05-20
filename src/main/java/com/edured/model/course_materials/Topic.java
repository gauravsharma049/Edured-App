package com.edured.model.course_materials;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(unique = true)
    private String slug;
    private String dateOfCreation;
    private String lastUpdatedDate;
//    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    private Lesson lesson;
    @OneToMany(mappedBy ="topic", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
