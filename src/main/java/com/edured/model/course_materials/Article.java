package com.edured.model.course_materials;

import com.edured.model.users.EduredUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    private EduredUser writer;
}
