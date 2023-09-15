package com.thewayhome.project.repository;

import com.thewayhome.project.dto.company.CompanyGetFullNameResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyRepositoryCustom {

    Page<CompanyGetFullNameResponseDto> searchCompanyByPartName(Pageable page, String part);
}
