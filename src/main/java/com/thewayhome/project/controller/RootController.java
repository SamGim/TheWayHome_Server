package com.thewayhome.project.controller;


import com.auth0.jwk.JwkException;
import com.thewayhome.project.domain.User;
import com.thewayhome.project.dto.user.UserRegisterRequestDto;
import com.thewayhome.project.service.ImageService;
import com.thewayhome.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
//@RequestMapping("/heartbeat")
public class RootController {
    private final ImageService imageService;
    private final UserService userService;

    @Autowired
    public RootController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("/heartbeat")
    @Operation(summary = "서버 생사", description = "서버 생사 확인")
    public ResponseEntity<Object> isServerAlive(){
//        throw new CustomException(TEST_ERROR);
        userService.test();
        return ResponseEntity.ok("connected");
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestBody UserRegisterRequestDto userRegisterRequestDto
            ) throws ParseException, JwkException {
        User user = userService.registerUser(userRegisterRequestDto);
        return ResponseEntity.ok(user);
    }
}
