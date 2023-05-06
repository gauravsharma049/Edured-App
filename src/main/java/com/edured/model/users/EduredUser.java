package com.edured.model.users;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class EduredUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    // add unique username later
    @Column(unique=true)
    private String email;
    @Column(unique=true)
    private String username;
    private String role;
    private String password;
    private String registeredDate;
}
