package com.tworld.celebring.util.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Tag(name = "util", description = "공통 기능 API")
@RestController
public class UtilController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openapi.naver.local.search.url}")
    private String NAVER_LOCAL_SEARCH_URL;

    @Value("${openapi.naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${openapi.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    @Operation(summary = "get local search api result", description = "네이버 지역 검색 API 결과 반환")
    @Parameters({
            @Parameter(name = "query", description = "검색어", example = "노티드 강남", required = true)
    })
    @GetMapping("local")
    public ResponseEntity<?> getLocalSearch(@RequestParam(value = "query") String query) {

        String openapiUrl = NAVER_LOCAL_SEARCH_URL;
        openapiUrl += "?query=" + query + "&display=5";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
        header.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        HttpEntity<?> httpEntity = new HttpEntity<HttpHeaders>(header);

        ResponseEntity<String> response;
        response = restTemplate.exchange(openapiUrl, HttpMethod.GET, httpEntity, String.class);

        return response;
    }
}
