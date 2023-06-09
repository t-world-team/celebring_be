package com.tworld.celebring.celeb.controller;

import com.tworld.celebring.celeb.dto.CelebAddDto;
import com.tworld.celebring.celeb.dto.CelebDto;
import com.tworld.celebring.celeb.dto.CelebInfoDto;
import com.tworld.celebring.celeb.dto.CelebRequestDto;
import com.tworld.celebring.celeb.model.Celeb;
import com.tworld.celebring.celeb.model.CelebLink;
import com.tworld.celebring.celeb.model.CelebSearch;
import com.tworld.celebring.celeb.service.CelebService;
import com.tworld.celebring.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "celeb", description = "셀럽 API")
@RequestMapping("celeb")
@RestController
public class CelebController {

    @Autowired
    private CelebService celebService;

    @Autowired
    private LoginService loginService;

    @Operation(summary = "add celeb", description = "셀럽 추가 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED(권한 없음)")
    })
    @PostMapping("")
    public ResponseEntity<?> addCeleb(@RequestBody CelebRequestDto celebRequestDto, Authentication authentication) {
        System.out.println(celebRequestDto);
        try {
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());

            if(userInfo.isPresent()){
                Celeb celeb = celebService.saveCeleb(userInfo.get().getId(), celebRequestDto.getCeleb());

                if(celebRequestDto.getMembers().size() > 0) {
                    for(CelebAddDto memberAdd : celebRequestDto.getMembers()) {
                        memberAdd.setKeywords(celeb.getName());
                        Celeb member = celebService.saveCeleb(userInfo.get().getId(), memberAdd);
                        CelebLink link = celebService.saveCelebLink(userInfo.get().getId(), celeb.getId(), member.getId());
                    }
                }

                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "get whole celeb list", description = "전체 셀럽 목록 조회")
    @GetMapping("list")
    public ResponseEntity<?> getCelebList() {
        List<CelebDto> list = celebService.getCelebList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "get celeb list grouping by group/solo, consonant", description = "그룹/솔로별, 초성별 셀럽 목록 조회")
    @GetMapping("list/consonant")
    public ResponseEntity<?> getCelebListByConsonant(Authentication authentication) {

        com.tworld.celebring.user.model.User user = null;
        if(authentication != null) {
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());
            user = userInfo.get();
        }

        Map<String, Object> list = new HashMap<>();
        List<Map<String, Object>> groupList = new ArrayList<>();
        List<Map<String, Object>> soloList = new ArrayList<>();
        for(CelebSearch search : CelebSearch.values()) {
            // group
            Map<String, Object> groupMap = new HashMap<>();
            groupMap.put("key", search.getConsonant());
            groupMap.put("list", celebService.getGroupCelebListGroupingByConsonant(search.getStartConsonant(), search.getEndConsonant(), user));
            groupList.add(groupMap);
            // solo
            Map<String, Object> soloMap = new HashMap<>();
            soloMap.put("key", search.getConsonant());
            soloMap.put("list", celebService.getSoloCelebListGroupingByConsonant(search.getStartConsonant(), search.getEndConsonant(), user));
            soloList.add(soloMap);
        }
        list.put("group", groupList);
        list.put("solo", soloList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "get favorite celeb list", description = "좋아요(즐겨찾기)한 셀럽 목록 조회")
    @GetMapping("favorite")
    public ResponseEntity<?> getFavoriteCelebList(Authentication authentication) {

        try {
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());

            if(userInfo.isPresent()){
                List<CelebDto> list = celebService.getFavoriteCelebByUserId(userInfo.get().getId());
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "get sub celeb list", description = "하위 셀럽 목록 조회")
    @GetMapping("sub")
    @Parameters({
            @Parameter(name = "celebId", description = "셀럽 ID", example = "1", required = true)
    })
    public ResponseEntity<?> getSubCelebList(@RequestParam(value = "celebId") Long celebId, Authentication authentication) {

        com.tworld.celebring.user.model.User user = null;
        if(authentication != null) {
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());
            user = userInfo.get();
        }

        List<CelebDto> list = celebService.getSubCelebList(celebId, user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "get celeb info and image list", description = "셀럽 상세 정보 및 이미지 목록 조회")
    @GetMapping("{celebId}")
    @Parameters({
            @Parameter(name = "celebId", description = "셀럽 ID", example = "1", required = true)
    })
    public ResponseEntity<?> getCelebImageList(@PathVariable Long celebId) {
        CelebInfoDto list = celebService.getCelebInfo(celebId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "add favorite celeb", description = "셀럽 좋아요(즐겨찾기) 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED(권한 없음)")
    })
    @PostMapping("like")
    public ResponseEntity<?> addFavoriteCeleb(@RequestBody Map<String, Long> paramMap, Authentication authentication) {

        try {
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());

            if(userInfo.isPresent()){
                celebService.saveCelebLike(userInfo.get().getId(), paramMap.get("celebId"));
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "delete favorite celeb", description = "셀럽 좋아요(즐겨찾기) 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED(권한 없음)")
    })
    @DeleteMapping("dislike")
    public ResponseEntity<?> deleteFavoriteCeleb(@RequestBody Map<String, Long> paramMap, Authentication authentication) {

        try {
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());

            if(userInfo.isPresent()){
                celebService.deleteCelebLike(userInfo.get().getId(), paramMap.get("celebId"));
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
