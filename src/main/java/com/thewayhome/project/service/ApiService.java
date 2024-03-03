package com.thewayhome.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thewayhome.project.domain.Company;
import com.thewayhome.project.domain.RealComplex;
import com.thewayhome.project.dto.complex.ComplexTimeDto;
import com.thewayhome.project.dto.complex.SPLResponseDto;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.utils.APIConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ApiService {
    private final APIConnector apiConnector;
//    private final String baseURL = "http://localhost:8080";
    private final String baseURL = "http://wasuphj.synology.me:8080";
    @Autowired
    public ApiService(APIConnector apiConnector) {
        this.apiConnector = apiConnector;
    }

    public List<ComplexTimeDto> findComplexIdsByCompanyId(Long companyId) throws CustomException {
        String path = "/complexIds/" + String.format("%012d", companyId);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        // companyId 를 12자리 스트링으로 변환
        Mono<String> jsonArrayString = APIConnector.getDataFromAPI(baseURL, path, null, queryParams);

        return jsonArrayString.flatMapMany(jsonString -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonString);
                JsonNode itemsArray = rootNode.get("items");
                if (itemsArray.isArray()) {
                    return Flux.fromIterable(itemsArray::elements);
                } else {
                    log.error("itemsArray is not array");
                    return Flux.empty();
                }
            } catch (IOException e) {
                log.error("e = {}", e);
                return Flux.error(e);
            }
        }).map(itemString -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(itemString.toString(), ComplexTimeDto.class);
            } catch (IOException e) {
                log.error("e = {}", e);
                throw new CustomException(CustomError.WEB_FLUX_ERROR);
            }
        }).collectList().block();
    }

    public List<ComplexTimeDto> findComplexIdsByLocation(Long companyId, List<Long> complexIds) throws CustomException {
        String path = "/complexIds/" + String.format("%012d", companyId);

        MultiValueMap<String, Object> queryBody = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        // companyId 를 12자리 스트링으로 변환
        List<String> complexIdsString = new ArrayList<String>();

        for (Long complexId : complexIds) {
            complexIdsString.add(String.format("%012d", complexId));
        }
        queryBody.add("complexIds", complexIdsString);
        Mono<String> jsonArrayString = APIConnector.getDataFromAPI(baseURL, path, queryBody, queryParams);

        return jsonArrayString.flatMapMany(jsonString -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonString);
                JsonNode itemsArray = rootNode.get("items");
                if (itemsArray.isArray()) {
                    return Flux.fromIterable(itemsArray::elements);
                } else {
                    log.error("itemsArray is not array");
                    return Flux.empty();
                }
            } catch (IOException e) {
                log.error("e = {}", e);
                return Flux.error(e);
            }
        }).map(itemString -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(itemString.toString(), ComplexTimeDto.class);
            } catch (IOException e) {
                log.error("e = {}", e);
                throw new CustomException(CustomError.WEB_FLUX_ERROR);
            }
        }).collectList().block();
    }

    public List<SPLResponseDto> findComplexAndPath(long complexId, long companyId) {
        String path = "/spl";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        // companyId 를 12자리 스트링으로 변환
        queryParams.add("companyId", String.format("%012d", companyId));
        queryParams.add("complexId", String.format("%012d", complexId));
        Mono<String> jsonArrayString = APIConnector.getDataFromAPI(baseURL, path, null, queryParams);

        return jsonArrayString.flatMapMany(jsonString -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonString);
                JsonNode itemsArray = rootNode.get("items");
                if (itemsArray.isArray()) {
                    return Flux.fromIterable(itemsArray::elements);
                } else {
                    log.error("itemsArray is not array");
                    return Flux.empty();
                }
            } catch (IOException e) {
                log.error("e = {}", e);
                return Flux.error(e);
            }
        }).map(itemString -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(itemString.toString(), SPLResponseDto.class);
            } catch (IOException e) {
                log.error("e = {}", e);
                throw new CustomException(CustomError.WEB_FLUX_ERROR);
            }
        }).collectList().block();
    }

    public void uploadCompanyData(Company companyRequestDto) {
        String path = "/company/upload";
        HashMap<String, Object> queryBody = new HashMap<>();
        queryBody.put("id", companyRequestDto.getCompanyId());
        queryBody.put("companyName", companyRequestDto.getCompanyName());
        queryBody.put("address", companyRequestDto.getAddress());
        queryBody.put("latitude", companyRequestDto.getLatitude());
        queryBody.put("longitude", companyRequestDto.getLongitude());
        Mono<String> response = APIConnector.postDataToAPI(baseURL, path, queryBody);
        log.info("response = {}", response.block());
        if (!Objects.equals(response.block(), "ok")) {
            log.error("Error uploading company data, response = {}", response);
        }
    }

    public void uploadComplexData(RealComplex realComplexRequestDto) {
        String path = "/complex/upload";
        HashMap<String, Object> queryBody = new HashMap<>();
        queryBody.put("id", realComplexRequestDto.getId());
        queryBody.put("name", realComplexRequestDto.getName());
        queryBody.put("latitude", realComplexRequestDto.getLatitude());
        queryBody.put("longitude", realComplexRequestDto.getLongitude());
        Mono<String> response = APIConnector.postDataToAPI(baseURL, path, queryBody);
        log.info("response = {}", response);
        if (!Objects.equals(response.block(), "ok")) {
            log.error("Error uploading complex data, response = {}", response);
        }
    }
}
