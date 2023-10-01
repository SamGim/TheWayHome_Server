package com.thewayhome.project.service;

import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.dto.complex.ComplexCardRequestDto;
import com.thewayhome.project.dto.complex.ComplexDetailRequestDto;
import com.thewayhome.project.dto.complex.ComplexSimpleRequestDto;
import com.thewayhome.project.dto.complex.ComplexSimpleRequestDto2;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.repository.ComplexRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplexService {
    private final ComplexRepository complexRepository;
    private final NaverMapsService naverMapsService;

    @Autowired
    public ComplexService(ComplexRepository complexRepository, NaverMapsService naverMapsService) {
        this.complexRepository = complexRepository;
        this.naverMapsService = naverMapsService;
    }

    public List<ComplexSimpleRequestDto> getComplexesInBoundingBox(double swLng, double swLat, double neLng, double neLat) {
        List<Complex> withinMap = complexRepository.findComplexesInBoundingBox(swLng, swLat, neLng, neLat);

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

    @Transactional
    public void updatePointColumn() throws ParseException {
        List<Complex> complexes = complexRepository.findAll();

        for (Complex complex : complexes) {
            Double latitude = complex.getLatitude();
            Double longitude = complex.getLongitude();

            Point point = (latitude != null && longitude != null)
                    ? (Point) new WKTReader().read(String.format("POINT(%s %s)", latitude, longitude))
                    : null;

            complex.setLocation(point);
            complexRepository.save(complex);
        }
    }

    public List<ComplexSimpleRequestDto2> getComplexesInBoundingBox2(double swLng, double swLat, double neLng, double neLat, String coPoint) {
        List<Complex> withinMap = complexRepository.findComplexesInBoundingBox(swLng, swLat, neLng, neLat);
        return withinMap.stream()
                .map(x -> ComplexSimpleRequestDto2.fromEntity(x, naverMapsService.getComplexToCompanyTime(x.getLongitude() + "," + x.getLatitude(), coPoint)))
                .collect(Collectors.toList());
    }
}
