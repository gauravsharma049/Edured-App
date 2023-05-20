package com.edured.model.course_materials;

import com.edured.model.users.EduredUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String commentTime;
    private String commentContent;
    @JsonBackReference
    @ManyToOne
    private Topic topic;

    @OneToOne
    private EduredUser commentUser;
}
