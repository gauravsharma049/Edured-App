package com.edured.repository.files;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edured.model.files.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    Image findByFileName(String fileName);
}
