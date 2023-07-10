package com.edured.services.files;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.edured.model.files.Image;

public interface FileService {
    void uploadFile(MultipartFile file) throws IOException;
    List<Image> getImageFile();
    void uploadImage(String path, MultipartFile file) throws IOException;

    void getImageResource(String path, String imageName, HttpServletResponse response);
    void deleteImageResource(String path, String imageName) throws IOException;
}
