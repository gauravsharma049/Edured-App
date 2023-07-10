package com.edured.model.files;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private String imageVideoUrl;
    private long viewCount;
    private long clickCount;
    private String adLink;
    private long a;
    private long b;
    private long c;
    private long d;
}
