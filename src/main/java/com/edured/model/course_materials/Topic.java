package com.edured.model.course_materials;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

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
}
