package com.tworld.celebring.util.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tworld.celebring.util.service.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "util", description = "공통 기능 API")
@RestController
@RequiredArgsConstructor
public class UtilController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final CloudinaryService cloudinaryService;

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
        header.setContentType(MediaType.TEXT_PLAIN);
        header.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
        header.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        HttpEntity<?> httpEntity = new HttpEntity<>(header);

        ResponseEntity<String> response;
        response = restTemplate.exchange(openapiUrl, HttpMethod.GET, httpEntity, String.class);

        return response;
    }

    @Operation(summary = "image upload", description = "이미지 등록")
    @GetMapping("image")
    public ResponseEntity<?> imageUpload(@RequestPart("files") List<MultipartFile> files) {
        try {
            List<Map> result = cloudinaryService.unsignedUpload(files);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (UnknownError e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
