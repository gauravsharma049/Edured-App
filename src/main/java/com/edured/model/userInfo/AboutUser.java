package com.edured.model.userInfo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AboutUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String skills, githubUrl, linkedinUrl, websiteUrl, about;
    @OneToMany(mappedBy ="aboutUser", cascade = CascadeType.ALL)
    private List<UserEducation> userEducations;
    @OneToMany(mappedBy = "aboutUser",cascade = CascadeType.ALL)
    private List<UserWorkExperience> userWorkExperiences;
    
}
