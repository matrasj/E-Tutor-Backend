package com.example.etutorbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class StorageService {
    private final static String FOLDER_PATH = "C:\\Users\\jkobm\\WebstormProjects\\ETutorFrontned\\src\\";
    public String uploadProfileImageToFileSystem(MultipartFile multipartFile) throws IOException {
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();

        multipartFile.transferTo(new File(FOLDER_PATH + "assets\\images\\profile-images\\" + fileName));

        return "assets\\images\\profile-images\\" + fileName;
    }

    public void deleteProfileImageFromFileSystem(String imagePath) {
        File file = new File("C:\\Users\\jkobm\\WebstormProjects\\ETutorFrontned\\src\\" + imagePath);
        file.delete();
    }
}
