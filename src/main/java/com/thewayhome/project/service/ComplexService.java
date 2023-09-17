package com.thewayhome.project.service;

import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.dto.complex.ComplexCardRequestDto;
import com.thewayhome.project.dto.complex.ComplexDetailRequestDto;
import com.thewayhome.project.dto.complex.ComplexSimpleRequestDto;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.repository.ComplexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplexService {
    private final ComplexRepository complexRepository;

    @Autowired
    public ComplexService(ComplexRepository complexRepository) {
        this.complexRepository = complexRepository;
    }

    public List<ComplexSimpleRequestDto> getComplexesInBoundingBox(double swLng, double swLat, double neLng, double neLat) {
        List<Complex> withinMap = complexRepository.findWithinMap(swLng, swLat, neLng, neLat);

        return withinMap.stream()
                .map(ComplexSimpleRequestDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ComplexCardRequestDto getComplexCardInfo(Long id){
        Complex complex = complexRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.WRONG_ID_ERROR));
        return ComplexCardRequestDto.fromEntity(complex);
    }
    public ComplexDetailRequestDto getComplexDetailInfo(Long id){
        Complex complex = complexRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.WRONG_ID_ERROR));
        return ComplexDetailRequestDto.fromEntity(complex);
    }
}
