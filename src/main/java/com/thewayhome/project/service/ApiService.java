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
        String path = "/complexIds";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        // companyId 를 12자리 스트링으로 변환
        queryParams.add("companyId", String.format("%012d", companyId));
        Mono<String> jsonArrayString = APIConnector.getDataFromAPI(baseURL, path, queryParams);

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
        Mono<String> jsonArrayString = APIConnector.getDataFromAPI(baseURL, path, queryParams);

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
        MultiValueMap<String, Object> queryBody = new LinkedMultiValueMap<>();
        queryBody.add("id", companyRequestDto.getCompanyId());
        queryBody.add("companyName", companyRequestDto.getCompanyName());
        queryBody.add("address", companyRequestDto.getAddress());
        queryBody.add("latitude", companyRequestDto.getLatitude());
        queryBody.add("longitude", companyRequestDto.getLongitude());
        Mono<String> response = APIConnector.postDataToAPI(baseURL, path, queryBody);
        if (!Objects.equals(response.block(), "ok")) {
            log.error("Error uploading company data, response = {}", response);
        }
    }

    public void uploadComplexData(RealComplex realComplexRequestDto) {
        String path = "/complex/upload";
        MultiValueMap<String, Object> queryBody = new LinkedMultiValueMap<>();
        queryBody.add("id", realComplexRequestDto.getId());
        queryBody.add("name", realComplexRequestDto.getName());
        queryBody.add("latitude", realComplexRequestDto.getLatitude());
        queryBody.add("longitude", realComplexRequestDto.getLongitude());
        Mono<String> response = APIConnector.postDataToAPI(baseURL, path, queryBody);
        if (!Objects.equals(response.block(), "ok")) {
            log.error("Error uploading complex data, response = {}", response);
        }
    }
}
