package com.thewayhome.project.controller;


import com.thewayhome.project.domain.Company;
import com.thewayhome.project.dto.company.CompanyGetFullNameResponseDto;
import com.thewayhome.project.dto.company.CompanyRegisterRequestDto;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService =  companyService;
    }

    // 부분 이름으로 전체 회사명이 있는지 검색한다.
    @GetMapping("/suggest/name")
    public ResponseEntity<Object> getCompanyFullName(
            Pageable pageable,
            @RequestParam String part
    ){
        Page<CompanyGetFullNameResponseDto> companyNameList = companyService.searchCompanyNameByPart(pageable, part);
        return ResponseEntity.ok(companyNameList);
    }

    // 회사 저장
    @PostMapping
    public ResponseEntity<Object> registerCompany(@RequestBody CompanyRegisterRequestDto companyDTO) {
        return ResponseEntity.ok(companyService.registerCompany(companyDTO));
    }

    // 회사 id로 좌표 쿼리
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCompanyById(
            @PathVariable Long id
    ){
        Company company = companyService.getCompanyById(id).orElseThrow(()-> new CustomException(CustomError.NO_DATA_ERROR));
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("latitude", company.getLatitude());
        responseMap.put("longitude", company.getLongitude());
        return ResponseEntity.ok(responseMap);
    }

}
