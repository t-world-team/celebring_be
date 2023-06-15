package com.tworld.celebring.celeb.controller;

import com.tworld.celebring.celeb.dto.CelebDto;
import com.tworld.celebring.celeb.model.CelebSearch;
import com.tworld.celebring.celeb.service.CelebService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Operation(summary = "get celeb list grouping by group/solo, consonant", description = "그룹/솔로별, 초성별 셀럽 목록 조회")
    @GetMapping("list/consonant")
    public ResponseEntity<?> getCelebListByConsonant() {
        Map<String, Object> list = new HashMap<>();
        List<Map<String, Object>> groupList = new ArrayList<>();
        List<Map<String, Object>> soloList = new ArrayList<>();
        for(CelebSearch search : CelebSearch.values()) {
            // group
            Map<String, Object> groupMap = new HashMap<>();
            groupMap.put("key", search.getConsonant());
            groupMap.put("list", celebService.getGroupCelebListGroupingByConsonant(search.getStartConsonant(), search.getEndConsonant()));
            groupList.add(groupMap);
            // solo
            Map<String, Object> soloMap = new HashMap<>();
            soloMap.put("key", search.getConsonant());
            soloMap.put("list", celebService.getSoloCelebListGroupingByConsonant(search.getStartConsonant(), search.getEndConsonant()));
            soloList.add(soloMap);
        }
        list.put("group", groupList);
        list.put("solo", soloList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
