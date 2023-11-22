package com.thewayhome.project.service;

import com.thewayhome.project.domain.Company;
import com.thewayhome.project.dto.company.CompanyGetFullNameResponseDto;
import com.thewayhome.project.dto.company.CompanyRegisterRequestDto;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }
    private final CompanyRepository companyRepository;

    public Page<CompanyGetFullNameResponseDto> searchCompanyNameByPart(Pageable page, String part) {
        return companyRepository.searchCompanyByPartName(page, part);
    }

    public Company registerCompany(CompanyRegisterRequestDto companyDto) {
        // CompanyDTO에서 필요한 데이터를 가져와서 Company 엔티티로 변환
        Company company = companyDto.toEntity();
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());
        company.setCreatedBy("admin");
        company.setUpdatedBy("admin");
        // 회사 등록
        try{
            return companyRepository.save(company);
        } catch (Exception e) {
            System.out.println("e = " + e);
            throw new CustomException(CustomError.DB_SAVE_ERROR);
        }
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }
}
