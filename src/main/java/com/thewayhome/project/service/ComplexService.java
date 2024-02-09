package com.thewayhome.project.service;

import com.thewayhome.project.domain.*;
import com.thewayhome.project.dto.complex.*;
import com.thewayhome.project.dto.image.ComplexImageResponseDto;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComplexService {
    private final NaverMapsService naverMapsService;
    private final ImageService imageService;
    private final RealComplexRepository realComplexRepository;
    private final ComplexImageRepository complexImageRepository;
    private final SecurityFacilityRepository securityFacilityRepository;
    private final EtcFacilityRepository etcFacilityRepository;
    private final CoolingFacilityRepository coolingFacilityRepository;
    private final MaintenanceCostIncludingsRepository maintenanceCostIncludingsRepository;
    private final LivingFacilityRepository livingFacilityRepository;
    private final HeatingFacilityRepository heatingFacilityRepository;
    private final ApiService apiService;
    @Autowired
    public ComplexService(NaverMapsService naverMapsService, ImageService imageService, RealComplexRepository realComplexRepository, ComplexImageRepository complexImageRepository, SecurityFacilityRepository securityFacilityRepository, EtcFacilityRepository etcFacilityRepository, CoolingFacilityRepository coolingFacilityRepository, MaintenanceCostIncludingsRepository maintenanceCostIncludingsRepository, LivingFacilityRepository livingFacilityRepository, HeatingFacilityRepository heatingFacilityRepository, ApiService apiService) {
        this.naverMapsService = naverMapsService;
        this.imageService = imageService;
        this.realComplexRepository = realComplexRepository;
        this.complexImageRepository = complexImageRepository;
        this.securityFacilityRepository = securityFacilityRepository;
        this.etcFacilityRepository = etcFacilityRepository;
        this.coolingFacilityRepository = coolingFacilityRepository;
        this.maintenanceCostIncludingsRepository = maintenanceCostIncludingsRepository;
        this.livingFacilityRepository = livingFacilityRepository;
        this.heatingFacilityRepository = heatingFacilityRepository;
        this.apiService = apiService;
    }


    // 2
    public RealComplexCardResponseDto getRealComplexCardInfo(Long id){
        RealComplex complex = realComplexRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.WRONG_ID_ERROR));
        return RealComplexCardResponseDto.fromEntity(complex, 1800000);
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


    // 양끝 좌표 내 매물 들을 각각 Company까지의 시간과 함께 반환
    public List<RealComplexSimpleResponseDto> getRealComplexesInBoundingBox(double swLng, double swLat, double neLng, double neLat, Long cpId) {
        List<RealComplex> withinMap = realComplexRepository.findComplexesInBoundingBox(swLng, swLat, neLng, neLat);
        // RealComplexSimpleResponseDto에 직장까지 소요시간 추가하고 소요시간으로 리스트를 정렬해서 반환
        return withinMap.stream()
                .map(complex -> RealComplexSimpleResponseDto.fromEntity(complex, (int)(Math.random() % 60)))
                .sorted((o1, o2) -> o1.getDuration() - o2.getDuration())
                .collect(Collectors.toList());

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

            // CoolingFacility 저장
            if (complexDto.getCoolingFacility() != null) {
                CoolingFacility coolingFacility = complexDto.getCoolingFacility().toEntity();
                coolingFacilityRepository.save(coolingFacility);
                complex.setCoolingFacility(coolingFacility);
            }

            // SecurityFacility 저장
            if (complexDto.getSecurityFacility() != null) {
                SecurityFacility securityFacility = complexDto.getSecurityFacility().toEntity();
                securityFacilityRepository.save(securityFacility);
                complex.setSecurityFacility(securityFacility);
            }

            // EtcFacility 저장
            if (complexDto.getEtcFacility() != null) {
                EtcFacility etcFacility = complexDto.getEtcFacility().toEntity();
                etcFacilityRepository.save(etcFacility);
                complex.setEtcFacility(etcFacility);
            }

            // LivingFacility 저장
            if (complexDto.getLivingFacility() != null) {
                LivingFacility livingFacility = complexDto.getLivingFacility().toEntity();
                livingFacilityRepository.save(livingFacility);
                complex.setLivingFacility(livingFacility);
            }

            // MaintenanceCostIncludings 저장
            if (complexDto.getMaintenanceCostIncludings() != null) {
                MaintenanceCostIncludings maintenanceCostIncludings = complexDto.getMaintenanceCostIncludings().toEntity();
                maintenanceCostIncludingsRepository.save(maintenanceCostIncludings);
                complex.setMaintenanceCostIncludings(maintenanceCostIncludings);
            }

            // heatingFacility 저장
            if (complexDto.getHeatingFacility() != null) {
                HeatingFacility heatingFacility = complexDto.getHeatingFacility().toEntity();
                heatingFacilityRepository.save(heatingFacility);
                complex.setHeatingFacility(heatingFacility);
            }

            // baseEntity 저장
            complex.setCreatedAt(LocalDateTime.now());
            complex.setUpdatedAt(LocalDateTime.now());
            complex.setCreatedBy("admin");
            complex.setUpdatedBy("admin");

            complex.setRoomImages(roomImages);
            RealComplex cp = realComplexRepository.save(complex);
            apiService.uploadComplexData(cp);

            return RealComplexDetailResponseDto.fromEntity(complex, mImageDto, roomImagesDto);

        } catch (ParseException e) {
            System.out.println("e = " + e);
            throw new CustomException(CustomError.PARSE_ERROR);
        } catch (Exception e){
            System.out.println("e = " + e);
            throw new CustomException(CustomError.DB_SAVE_ERROR);
        }
    }

    public List<RealComplexSimpleResponseDto> getRealComplexesByCompanyId(Long companyId) {
        List<RealComplexSimpleResponseDto> rtn = new ArrayList<>();

        List<ComplexTimeDto> complexIds = apiService.findComplexIdsByCompanyId(companyId);

        log.info("api 요청결과 complex&times = " + complexIds);
        for(ComplexTimeDto complex : complexIds){
            Long id = complex.getId();
            Integer duration = complex.getDuration();
            RealComplex realComplex = realComplexRepository.findById(id).orElseThrow(() -> new CustomException(CustomError.WRONG_ID_ERROR));
            RealComplexSimpleResponseDto realComplexSimpleResponseDto = RealComplexSimpleResponseDto.fromEntity(realComplex, duration);
            rtn.add(realComplexSimpleResponseDto);
        }
        return rtn;
    }
    public List<ComplexTimeDto> getTestComplexIds(Long companyId) {
        return apiService.findComplexIdsByCompanyId(companyId);
    }

    public RealComplexDetailWithPathResponseDto getRealComplexDetailWithPath(long complexId, long companyId) {
        RealComplexDetailResponseDto complex =  this.getRealComplexDetailInfo(complexId);
        List<SPLResponseDto> spls = apiService.findComplexAndPath(complexId, companyId);
        return RealComplexDetailWithPathResponseDto.builder()
                .complex(complex)
                .path(spls)
                .build();

    }
}
