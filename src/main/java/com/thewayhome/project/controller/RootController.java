package com.thewayhome.project.controller;


import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.service.ComplexService;
import com.thewayhome.project.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.thewayhome.project.exception.CustomError.TEST_ERROR;

@Slf4j
@RestController
//@RequestMapping("/heartbeat")
public class RootController {
    private final ImageService imageService;

    @Autowired
    public RootController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/heartbeat")
    public ResponseEntity<Object> isServerAlive(){
//        throw new CustomException(TEST_ERROR);
        return ResponseEntity.ok("connected");
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }
}
