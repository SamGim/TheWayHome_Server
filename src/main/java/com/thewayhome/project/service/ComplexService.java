package com.thewayhome.project.service;

import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.domain.ComplexImage;
import com.thewayhome.project.domain.RealComplex;
import com.thewayhome.project.dto.complex.*;
import com.thewayhome.project.dto.image.ComplexImageResponseDto;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.repository.ComplexImageRepository;
import com.thewayhome.project.repository.ComplexRepository;
import com.thewayhome.project.repository.RealComplexRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplexService {
    private final ComplexRepository complexRepository;
    private final NaverMapsService naverMapsService;
    private final ImageService imageService;
    private final RealComplexRepository realComplexRepository;
    private final ComplexImageRepository complexImageRepository;
    @Autowired
    public ComplexService(ComplexRepository complexRepository, NaverMapsService naverMapsService, ImageService imageService, RealComplexRepository realComplexRepository, ComplexImageRepository complexImageRepository) {
        this.complexRepository = complexRepository;
        this.naverMapsService = naverMapsService;
        this.imageService = imageService;
        this.realComplexRepository = realComplexRepository;
        this.complexImageRepository = complexImageRepository;
    }

    public List<ComplexSimpleResponseDto> getComplexesInBoundingBox(double swLng, double swLat, double neLng, double neLat) {
        List<Complex> withinMap = complexRepository.findComplexesInBoundingBox(swLng, swLat, neLng, neLat);

        return withinMap.stream()
                .map(ComplexSimpleResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 2
    public List<RealComplexSimpleResponseDto> getRealComplexesInBoundingBox(double swLng, double swLat, double neLng, double neLat) {
        List<RealComplex> withinMap = realComplexRepository.findComplexesInBoundingBox(swLng, swLat, neLng, neLat);

        return withinMap.stream()
                .map(RealComplexSimpleResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


    public ComplexCardResponseDto getComplexCardInfo(Long id){
        Complex complex = complexRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.WRONG_ID_ERROR));
        return ComplexCardResponseDto.fromEntity(complex);
    }

    // 2
    public RealComplexCardResponseDto getRealComplexCardInfo(Long id){
        RealComplex complex = realComplexRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.WRONG_ID_ERROR));
        return RealComplexCardResponseDto.fromEntity(complex);
    }

    public ComplexDetailResponseDto getComplexDetailInfo(Long id){
        Complex complex = complexRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.WRONG_ID_ERROR));
        return ComplexDetailResponseDto.fromEntity(complex);
    }

    // 2
    public RealComplexDetailResponseDto getRealComplexDetailInfo(Long id){
        RealComplex complex = realComplexRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.WRONG_ID_ERROR));
        List<ComplexImage> images = complexImageRepository.findByRealComplex(complex);
        ComplexImageResponseDto mainImage = null;
        List<ComplexImageResponseDto> roomImages = new ArrayList<>();
        for(ComplexImage image : images){
            if (image.getIsMain()){
                mainImage = ComplexImageResponseDto.fromEntity(image);
            }
            else{
                roomImages.add(ComplexImageResponseDto.fromEntity(image));
            }
        }
        return RealComplexDetailResponseDto.fromEntity(complex, mainImage, roomImages);
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

    public List<ComplexSimpleResponseDto2> getComplexesInBoundingBox2(double swLng, double swLat, double neLng, double neLat, String coPoint) {
        List<Complex> withinMap = complexRepository.findComplexesInBoundingBox(swLng, swLat, neLng, neLat);
        return withinMap.stream()
                .map(x -> ComplexSimpleResponseDto2.fromEntity(x, naverMapsService.getComplexToCompanyTime(x.getLongitude() + "," + x.getLatitude(), coPoint)))
                .collect(Collectors.toList());
    }

    // 2
    public List<RealComplexSimpleResponseDto2> getRealComplexesInBoundingBox2(double swLng, double swLat, double neLng, double neLat, String coPoint) {
        List<RealComplex> withinMap = realComplexRepository.findComplexesInBoundingBox(swLng, swLat, neLng, neLat);
        return withinMap.stream()
                .map(x -> RealComplexSimpleResponseDto2.fromEntity(x, naverMapsService.getComplexToCompanyTime(x.getLongitude() + "," + x.getLatitude(), coPoint)))
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

    @Transactional
    public RealComplexDetailResponseDto registerRealComplex(RealComplexRegisterRequestDto complexDto, MultipartFile mImage, List<MultipartFile> rImages){
        try{
            RealComplex complex = complexDto.toEntity();
            ComplexImageResponseDto mImageDto = null;
            // 대표사진 저장
            if (mImage != null) {
                String mainImageUrl = imageService.uploadImage(mImage);
                ComplexImage mainImage = ComplexImage.builder()
                        .realComplex(complex)
                        .url(mainImageUrl)
                        .isMain(true)
                        .build();
                complexImageRepository.save(mainImage);
                complex.setMainImageUrl(mainImageUrl);
                mImageDto = ComplexImageResponseDto.fromEntity(mainImage);
            }

            // 방 사진들 저장
            List<ComplexImage> roomImages = new ArrayList<>();
            List<ComplexImageResponseDto> roomImagesDto = new ArrayList<>();
            if (rImages != null) {
                for (MultipartFile roomImageFile : rImages) {
                    String roomImageUrl = imageService.uploadImage(roomImageFile);
                    ComplexImage roomImage = ComplexImage.builder()
                            .realComplex(complex)
                            .url(roomImageUrl)
                            .isMain(false)
                            .build();
                    complexImageRepository.save(roomImage);
                    roomImages.add(roomImage);
                    roomImagesDto.add(ComplexImageResponseDto.fromEntity(roomImage));
                }
            }
            complex.setRoomImages(roomImages);
            realComplexRepository.save(complex);
            return RealComplexDetailResponseDto.fromEntity(complex, mImageDto, roomImagesDto);

        } catch (ParseException e) {
            System.out.println("e = " + e);
            throw new CustomException(CustomError.PARSE_ERROR);
        } catch (Exception e){
            System.out.println("e = " + e);
            throw new CustomException(CustomError.DB_SAVE_ERROR);
        }
    }

    public void deleteComplex(long complexId) {
        try{
            complexRepository.deleteById(complexId);
        }catch (Exception e){
            throw new CustomException(CustomError.DB_DELETE_ERROR);
        }
    }
}
