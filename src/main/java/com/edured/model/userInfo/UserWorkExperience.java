package com.edured.model.userInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserWorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String companyName;
    private String position;
    private String startDate;
    private String endDate;
    private String description;
    @ManyToOne
    private AboutUser aboutUser;
}
