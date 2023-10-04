package com.thewayhome.project.service;

import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.dto.complex.*;
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

    public Complex registerComplex(ComplexRegisterRequestDto complexDto) {
        try{
            Complex complex = complexDto.toEntity();
            return complexRepository.save(complex);
        } catch (ParseException e) {
            System.out.println("e = " + e);
            throw new CustomException(CustomError.PARSE_ERROR);
        } catch (Exception e){
            System.out.println("e = " + e);
            throw new CustomException(CustomError.DB_SAVE_ERROR);
        }
    }
}
