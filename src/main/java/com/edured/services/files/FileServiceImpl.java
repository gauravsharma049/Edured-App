package com.edured.services.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.edured.model.files.Image;
import com.edured.repository.files.ImageRepository;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private ImageRepository fileRepository;

    @Override
    public List<Image> getImageFile() {
        return fileRepository.findAll();
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        Image image = new Image();
        String fileName = file.getOriginalFilename();
        fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf('.'));
        image.setFileName(fileName);

        File file2 = new ClassPathResource("static/files/images").getFile();
        Path path = Paths.get(file2.getAbsolutePath() + File.separator + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        fileRepository.save(image);
    }

    @Override
    public void uploadImage(String path, MultipartFile file) throws IOException {
        // file name
        Image image = new Image();
        String fileName = file.getOriginalFilename();
        fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf('.'));
        image.setFileName(fileName);

        // full path
        String filePath = path + File.separator + fileName;
        // create folder if not created
        File file2 = new File(path);
        if (!file2.exists()) {
            file2.mkdir();
        }
        // save file
        InputStream inputStream = file.getInputStream();
        Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();
        fileRepository.save(image);
    }

    @Override
    public void getImageResource(String path, String imageName, HttpServletResponse response) {
        String filePath = path + File.separator + imageName;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteImageResource(String path, String imageName) {
        String filePath = path + File.separator + imageName;

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try{
            fileRepository.delete(fileRepository.findByFileName(imageName));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
