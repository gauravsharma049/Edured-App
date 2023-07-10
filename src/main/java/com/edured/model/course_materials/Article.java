package com.edured.model.course_materials;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.edured.model.users.EduredUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {
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
    private boolean status;
    private boolean deleted;
    private long viewCount;
    @ManyToOne
    private EduredUser writer;
}
