package com.thewayhome.project.service;

import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${image.upload.directory}")
    private String imageUploadDirectory;


    public String uploadImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString().replace("-", "") + ".png";
//            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path uploadPath = Paths.get(imageUploadDirectory);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return "/images/" + fileName;
        } catch (IOException e) {
            throw new CustomException(CustomError.IMAGE_UPLOAD_ERROR);
        }
    }



}
