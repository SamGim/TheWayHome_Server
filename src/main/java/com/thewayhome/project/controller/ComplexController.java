package com.thewayhome.project.controller;

import com.thewayhome.project.dto.complex.*;
import com.thewayhome.project.service.ComplexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
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


    // 파라미터에 company id 추가
    @GetMapping("/info/inquery/{complexId}")
    @Operation(summary = "매물 조회", description = "id로 특정 매물 조회")
    public ResponseEntity<RealComplexDetailWithPathResponseDto> getRealComplexById(
            @Parameter(description = "매물 ID", required = true) @PathVariable long complexId,
            @Parameter(description = "직장ID", required = true) @RequestParam long companyId) {
        return ResponseEntity.ok(complexService.getRealComplexDetailWithPath(complexId, companyId));
    }


    // 2
    @GetMapping("/list/inquery")
    @Operation(summary = "영역 내 매물 조회", description = "영역 내 매물과 직장까지 소요시간 조회")
    public ResponseEntity<List<RealComplexSimpleResponseDto>> getRealComplexesWithinBoundingBox2(
            @Parameter(description = "지도 남서쪽 longitude", required = true) @RequestParam(name = "sw_lng") double swLng,
            @Parameter(description = "지도 남서쪽 latitude", required = true) @RequestParam(name = "sw_lat") double swLat,
            @Parameter(description = "지도 북동쪽 longitude", required = true) @RequestParam(name = "ne_lng") double neLng,
            @Parameter(description = "지도 북동쪽 latitude", required = true) @RequestParam(name = "ne_lat") double neLat,
            @Parameter(description = "직장 ID", required = true) @RequestParam(name = "cp_id") Long cpId) {
        return ResponseEntity.ok(complexService.getRealComplexesInBoundingBox(swLng, swLat, neLng, neLat, cpId));
    }

    // companyId로 매물 10개 조회
    @GetMapping("/list/inquery/{companyId}")
    @Operation(summary = "company 기준 매물 조회", description = "회사로 부터 소요시간 기준 10개 매물 조회")
    public ResponseEntity<List<RealComplexSimpleResponseDto>> getRealComplexesByCompanyId(
            @Parameter(description = "회사 ID", required = true) @PathVariable Long companyId) {
        return ResponseEntity.ok(complexService.getRealComplexesByCompanyId(companyId));
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