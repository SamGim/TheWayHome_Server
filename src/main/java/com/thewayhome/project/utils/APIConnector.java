package com.thewayhome.project.utils;

import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class APIConnector {
    public static Mono<String> getDataFromAPI (
            String endpoint,
            String path,
            Object queryBody,
            MultiValueMap<String, String> queryParams
    ) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                );

        WebClient client = WebClient.builder()
                .baseUrl(endpoint)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        WebClient.RequestBodySpec bodySpec = client.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParams(queryParams)
                        .build()
                );

        bodySpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .bodyValue(queryBody)
                .retrieve();

        return bodySpec.exchangeToMono(response -> {
            log.info("response = {}", response.statusCode());
            log.info("response = {}", response.bodyToMono(String.class));
            if (response.statusCode().is2xxSuccessful()) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                log.error("response = {}", response.bodyToMono(String.class));
                return Mono.error(new CustomException(CustomError.PTIS_SERVER_REQUEST_4XX_ERROR));
            } else {
                log.error("response = {}", response.bodyToMono(String.class));
                return Mono.error(new CustomException(CustomError.PTIS_SERVER_REQUEST_UNKNOWN_ERROR));
            }
        });
    }



    public static Mono<String> postDataToAPI (
            String endpoint,
            String path,
            HashMap<String, Object> queryBody
    ) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                );

        WebClient client = WebClient.builder()
                .baseUrl(endpoint)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        WebClient.RequestBodySpec bodySpec = client.method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .build()
                );

        bodySpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .bodyValue(queryBody)
                .retrieve();

        return bodySpec.exchangeToMono(response -> {
            if (response.statusCode().is2xxSuccessful()) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                return Mono.error(new CustomException(CustomError.PTIS_SERVER_REQUEST_4XX_ERROR));
            } else {
                return Mono.error(new CustomException(CustomError.PTIS_SERVER_REQUEST_UNKNOWN_ERROR));
            }
        });
    }
}
