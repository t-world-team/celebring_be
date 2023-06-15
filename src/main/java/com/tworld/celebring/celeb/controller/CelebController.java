package com.tworld.celebring.celeb.controller;

import com.tworld.celebring.celeb.dto.CelebDto;
import com.tworld.celebring.celeb.service.CelebService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "celeb", description = "셀럽 API")
@RequestMapping("celeb")
@RestController
public class CelebController {

    @Autowired
    private CelebService celebService;

    @Operation(summary = "get whole celeb list", description = "전체 셀럽 목록 조회")
    @GetMapping("list")
    public ResponseEntity<?> getCelebList() {
        List<CelebDto> list = celebService.getCelebList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
