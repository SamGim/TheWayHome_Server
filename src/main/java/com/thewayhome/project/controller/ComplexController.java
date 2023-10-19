package com.thewayhome.project.controller;

import com.thewayhome.project.dto.complex.*;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.service.ComplexService;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/complex")
public class ComplexController {
    private final ComplexService complexService;

    @Autowired
    public ComplexController(ComplexService complexService) {
        this.complexService = complexService;
    }

    // 매물 목록 쿼리
    @GetMapping("/list/inquery")
    public ResponseEntity<List<ComplexSimpleResponseDto>> getComplexesWithinBoundingBox(
            @RequestParam(name = "sw_lng") double swLng,
            @RequestParam(name = "sw_lat") double swLat,
            @RequestParam(name = "ne_lng") double neLng,
            @RequestParam(name = "ne_lat") double neLat) {
        return ResponseEntity.ok(complexService.getComplexesInBoundingBox(swLng, swLat, neLng, neLat));
    }

    // 2
    @GetMapping("/real/list/inquery")
    public ResponseEntity<List<RealComplexSimpleResponseDto>> getRealComplexesWithinBoundingBox(
            @RequestParam(name = "sw_lng") double swLng,
            @RequestParam(name = "sw_lat") double swLat,
            @RequestParam(name = "ne_lng") double neLng,
            @RequestParam(name = "ne_lat") double neLat) {
        return ResponseEntity.ok(complexService.getRealComplexesInBoundingBox(swLng, swLat, neLng, neLat));
    }

    // 단일 매물 detail 또는 card info 조회
    @GetMapping("/info/inquery/{complexId}")
    public ResponseEntity<Object> getComplexById(
            @PathVariable long complexId,
            @RequestParam Boolean detail) {
        if (detail){
            return ResponseEntity.ok(complexService.getComplexDetailInfo(complexId));
        } else {
            return ResponseEntity.ok(complexService.getComplexCardInfo(complexId));
        }
    }

    // 2
    @GetMapping("/real/info/inquery/{complexId}")
    public ResponseEntity<Object> getRealComplexById(
            @PathVariable long complexId,
            @RequestParam Boolean detail) {
        if (detail){
            return ResponseEntity.ok(complexService.getRealComplexDetailInfo(complexId));
        } else {
            return ResponseEntity.ok(complexService.getRealComplexCardInfo(complexId));
        }
    }

    @GetMapping("/update/location")
    public ResponseEntity<String> updateLocationDate(){
        try{
            complexService.updatePointColumn();
        } catch (ParseException e) {
            System.out.println("e = " + e);
            throw new CustomException(CustomError.PARSE_ERROR);
        }
        return ResponseEntity.ok("finish");
    }

    @GetMapping("/list/inquery2")
    public ResponseEntity<List<ComplexSimpleResponseDto2>> getComplexesWithinBoundingBox2(
            @RequestParam(name = "sw_lng") double swLng,
            @RequestParam(name = "sw_lat") double swLat,
            @RequestParam(name = "ne_lng") double neLng,
            @RequestParam(name = "ne_lat") double neLat,
            @RequestParam(name = "co_lng") double coLng,
            @RequestParam(name = "co_lat") double coLat) {
        String coPoint = coLng + "," + coLat;
        return ResponseEntity.ok(complexService.getComplexesInBoundingBox2(swLng, swLat, neLng, neLat, coPoint));
    }

    // 2
    @GetMapping("/real/list/inquery2")
    public ResponseEntity<List<RealComplexSimpleResponseDto2>> getRealComplexesWithinBoundingBox2(
            @RequestParam(name = "sw_lng") double swLng,
            @RequestParam(name = "sw_lat") double swLat,
            @RequestParam(name = "ne_lng") double neLng,
            @RequestParam(name = "ne_lat") double neLat,
            @RequestParam(name = "co_lng") double coLng,
            @RequestParam(name = "co_lat") double coLat) {
        String coPoint = coLng + "," + coLat;
        return ResponseEntity.ok(complexService.getRealComplexesInBoundingBox2(swLng, swLat, neLng, neLat, coPoint));
    }

    @PostMapping
    public ResponseEntity<Object> registerComplex(@RequestBody ComplexRegisterRequestDto complexDto){
        return ResponseEntity.ok(complexService.registerComplex(complexDto));
    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RealComplexDetailResponseDto> registerRealComplex(
            @RequestPart RealComplexRegisterRequestDto complexDto,
            @RequestPart MultipartFile mainImage,
            @RequestPart List<MultipartFile> roomImages

    ) {
        return ResponseEntity.ok(complexService.registerRealComplex(complexDto, mainImage, roomImages));
    }
}
