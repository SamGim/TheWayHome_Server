package com.thewayhome.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thewayhome.project.utils.CommonUtils;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class NaverMapsService {
    private final Environment env;

    @Autowired
    public NaverMapsService(Environment env) {
        this.env = env;
    }

    public Map<String, Object> requestNaverMaps(String start, String goal) {
        String baseUrl = env.getProperty("naver.maps.base-url");
        String clientKey = env.getProperty("naver.maps.client-key");
        String clientSecret = env.getProperty("naver.maps.client-secret");
        String apiUrl =  baseUrl + "?start=" + start + "&goal=" + goal;
        HttpURLConnection conn = null;
        OutputStream stream = null;
        BufferedReader bufferedReader = null;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // 헤더 설정
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientKey);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                returnMap = CommonUtils.convertJSONstringToMap(response.toString());
                return returnMap;
            } else {
                throw new RuntimeException("네이버 API 호출 실패. 응답 코드: " + responseCode);
            }

        } catch(JsonProcessingException e){
            System.out.println("e = " + e);
            throw new CustomException(CustomError.JSON_PROCESSING_ERROR);
        } catch (ProtocolException e) {
            System.out.println("e = " + e);
            throw new CustomException(CustomError.HTTP_PROTOCOL_ERROR);
        } catch (MalformedURLException e) {
            System.out.println("e = " + e);
            throw new CustomException(CustomError.MALFORMED_URL_ERROR);
        } catch (IOException e) {
            System.out.println("e = " + e);
            throw new CustomException(CustomError.JAVA_IO_ERROR);
        }

    }

    public Integer getComplexToCompanyTime(String start, String end){
        // resultCode 유형
        // 0 -> 길찾기 성공
        // 1 -> 출발지와 도착지가 동일
        // 2 -> 출발지 또는 도착지가 도로 주변이 아닌 경우
        // 3 -> 자동차 길찾기 결과 제공 불가
        // 4 -> 경유지가 도로 주변이 아닌 경우
        // 5 -> 요청 경로가 매우 긴 경우(경유지를 포함한 직선거리의 합이 1500km 이상인 경우)
        Map<String, Object> responseMap = requestNaverMaps(start, end);
        Integer code = (Integer) responseMap.get("code");
        if (!Objects.equals(code, 0)) {
            System.out.println("code = " + code);
            return 0;
        }
        else{
            Map<String, Object> route = (Map<String, Object>) responseMap.get("route");
            List<Map<String, Object>> traoptimal = (List<Map<String, Object>>) route.get("traoptimal");
            Map<String, Object> summary = (Map<String, Object>) traoptimal.get(0).get("summary");
            return(Integer) summary.get("duration");
        }
    }
}

