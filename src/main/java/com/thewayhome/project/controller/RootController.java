package com.thewayhome.project.controller;


import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.service.ComplexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.thewayhome.project.exception.CustomError.TEST_ERROR;

@Slf4j
@RestController
//@RequestMapping("/heartbeat")
public class RootController {
    private final ComplexService complexService;

    @Autowired
    public RootController(ComplexService complexService) {
        this.complexService = complexService;
    }
    @GetMapping("/heartbeat")
    public ResponseEntity<Object> isServerAlive(){
        throw new CustomException(TEST_ERROR);
//        return ResponseEntity.ok("connected");
    }

    @GetMapping("/complex")
    public List<Complex> getComplexesWithinBoundingBox(
            @RequestParam(name = "sw_lng") double swLng,
            @RequestParam(name = "sw_lat") double swLat,
            @RequestParam(name = "ne_lng") double neLng,
            @RequestParam(name = "ne_lat") double neLat) {
        return complexService.getComplexesInBoundingBox(swLng, swLat, neLng, neLat);
    }
}
