package com.thewayhome.project.controller;

import com.thewayhome.project.dto.complex.*;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.service.ComplexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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


    // 2
    @GetMapping("/info/inquery/{complexId}")
    @Operation(summary = "매물 조회", description = "id로 특정 매물 조회")
    public ResponseEntity<Object> getRealComplexById(
            @Parameter(description = "매물 ID", required = true) @PathVariable long complexId,
            @Parameter(description = "true일 경우 자세정보, false일 경우 카드 정보", required = true)  @RequestParam Boolean detail) {
        if (detail){
            return ResponseEntity.ok(complexService.getRealComplexDetailInfo(complexId));
        } else {
            return ResponseEntity.ok(complexService.getRealComplexCardInfo(complexId));
        }
    }


    // 2
    @GetMapping("/real/list/inquery2")
    @Operation(summary = "영역 내 매물 조회", description = "영역 내 매물과 직장까지 소요시간 조회")
    public ResponseEntity<List<RealComplexSimpleResponseDto2>> getRealComplexesWithinBoundingBox2(
            @Parameter(description = "지도 남서쪽 longitude", required = true) @RequestParam(name = "sw_lng") double swLng,
            @Parameter(description = "지도 남서쪽 latitude", required = true) @RequestParam(name = "sw_lat") double swLat,
            @Parameter(description = "지도 북동쪽 longitude", required = true) @RequestParam(name = "ne_lng") double neLng,
            @Parameter(description = "지도 북동쪽 latitude", required = true) @RequestParam(name = "ne_lat") double neLat,
            @Parameter(description = "직장 longitude", required = true) @RequestParam(name = "co_lng") double coLng,
            @Parameter(description = "직장 latitude", required = true) @RequestParam(name = "co_lat") double coLat) {
        String coPoint = coLng + "," + coLat;
        return ResponseEntity.ok(complexService.getRealComplexesInBoundingBox2(swLng, swLat, neLng, neLat, coPoint));
    }

    @PostMapping
    public ResponseEntity<Object> registerComplex(@RequestBody ComplexRegisterRequestDto complexDto){
        return ResponseEntity.ok(complexService.registerComplex(complexDto));
    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "매물 등록", description = "매물 등록")
    public ResponseEntity<RealComplexDetailResponseDto> registerRealComplex(
            @RequestPart RealComplexRegisterRequestDto complexDto,
            @RequestPart MultipartFile mainImage,
            @RequestPart List<MultipartFile> roomImages

    ) {
        return ResponseEntity.ok(complexService.registerRealComplex(complexDto, mainImage, roomImages));
    }

}