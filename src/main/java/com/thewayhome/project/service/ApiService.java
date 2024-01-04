package com.thewayhome.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
@Slf4j
public class ApiService {
    private final APIConnector apiConnector;

    @Autowired
    public ApiService(APIConnector apiConnector) {
        this.apiConnector = apiConnector;
    }

    public List<ComplexTimeDto> findComplexIdsByCompanyId(Long companyId) throws CustomException {
        String path = "/complexIds";
        String baseUrl = "http://localhost:8080";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        // companyId 를 12자리 스트링으로 변환
        queryParams.add("companyId", String.format("%012d", companyId));
        Mono<String> jsonArrayString = APIConnector.getDataFromAPI(baseUrl, path, queryParams);

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
        String baseUrl = "http://localhost:8080";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        // companyId 를 12자리 스트링으로 변환
        queryParams.add("companyId", String.format("%012d", companyId));
        queryParams.add("complexId", String.format("%012d", complexId));
        Mono<String> jsonArrayString = APIConnector.getDataFromAPI(baseUrl, path, queryParams);

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
}
