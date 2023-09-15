package com.thewayhome.project.service;

import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.repository.ComplexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplexService {
    private final ComplexRepository complexRepository;

    @Autowired
    public ComplexService(ComplexRepository complexRepository) {
        this.complexRepository = complexRepository;
    }

    public List<Complex> getComplexesInBoundingBox(double swLng, double swLat, double neLng, double neLat) {
        return complexRepository.findWithinMap(swLng, swLat, neLng, neLat);
    }
}
