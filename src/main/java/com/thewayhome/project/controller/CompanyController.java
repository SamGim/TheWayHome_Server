package com.thewayhome.project.controller;


import com.thewayhome.project.domain.Company;
import com.thewayhome.project.dto.company.CompanyGetFullNameResponseDto;
import com.thewayhome.project.dto.company.CompanyRegisterRequestDto;
import com.thewayhome.project.dto.company.CompanyResponseDto;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "회사 이름 검색", description = "부분을 포함하는 모든 회사 이름들을 출력해줍니다.")
    public ResponseEntity<Page<CompanyGetFullNameResponseDto>> getCompanyFullName(
            @Parameter(description = "페이징 정보, sort는 적지 말것", required = false)Pageable pageable,
            @Parameter(description = "검색할 이름의 부분", required = true) @RequestParam String part
    ){
        Page<CompanyGetFullNameResponseDto> companyNameList = companyService.searchCompanyNameByPart(pageable, part);
        return ResponseEntity.ok(companyNameList);
    }

    // 회사 저장
    @PostMapping
    @Operation(summary = "회사 등록", description = "회사를 등록합니다.")
    public ResponseEntity<Company> registerCompany(@RequestBody CompanyRegisterRequestDto companyDTO) {
        return ResponseEntity.ok(companyService.registerCompany(companyDTO));
    }

    // 회사 id로 좌표 쿼리
    @GetMapping("/{id}")
    @Operation(summary = "회사 조회", description = "ID를 이용해 해당 회사를 조회합니다.")
    public ResponseEntity<CompanyResponseDto> getCompanyById(
            @Parameter(description = "회사 ID", required = true)@PathVariable Long id
    ){
        Company company = companyService.getCompanyById(id).orElseThrow(()-> new CustomException(CustomError.NO_DATA_ERROR));
        CompanyResponseDto result = CompanyResponseDto.fromEntity(company);
        return ResponseEntity.ok(result);
    }

}
