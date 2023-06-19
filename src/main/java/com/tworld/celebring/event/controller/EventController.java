package com.tworld.celebring.event.controller;

import com.tworld.celebring.event.dto.EventDetailDto;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.model.EventLike;
import com.tworld.celebring.event.service.EventService;
import com.tworld.celebring.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "events", description = "이벤트 API")
@RequestMapping("/events")
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final LoginService loginService;

    @Operation(summary = "get now event list", description = "현재 진행중인 이벤트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0번부터 시작)", example = "0", required = true),
            @Parameter(name = "size", description = "한페이지에 나오는 개수", example = "10")
    })
    @GetMapping("")
    public ResponseEntity<?> getCurrentEventList(
            @RequestParam(value = "page") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<EventListDto> list = eventService.getCurrentEventList(PageRequest.of(page, size));  // page는 0부터 시작
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "celeb's event list", description = "셀럽의 이벤트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Parameters({
            @Parameter(name = "celebId", description = "셀럽 ID", example = "2", required = true),
            @Parameter(name = "page", description = "페이지 번호 (0번부터 시작)", example = "0", required = true),
            @Parameter(name = "size", description = "한페이지에 나오는 개수", example = "10")
    })
    @GetMapping("/{celebId}")
    public ResponseEntity<?> getCelebEventList(
            @PathVariable int celebId,
            @RequestParam(value = "page") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<EventListDto> list = eventService.getCelebEventList((long) celebId, page, size);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "event detail", description = "이벤트 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Parameters({
            @Parameter(name = "eventId", description = "이벤트 ID", example = "1", required = true),
    })
    @GetMapping("/detail/{eventId}")
    public ResponseEntity<?> getEventDetail(@PathVariable Long eventId, Authentication authentication) {
        Long userId = 0L;
        if (authentication != null) {  // 로그인 한 경우에만 좋아요 여부 조회
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());
            userId = userInfo.get().getId();
        }
        EventDetailDto content = eventService.getEventDetail(eventId, userId);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @Operation(summary = "add favorite event", description = "이벤트 좋아요(즐겨찾기) 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED(권한 없음)")
    })
    @Parameters({
            @Parameter(name = "eventId", description = "이벤트 ID", example = "1", required = true),
    })
    @PostMapping("/likes")
    public ResponseEntity<?> addFavoriteEvent(@RequestBody  Map<String, Long> paramMap, Authentication authentication) {
        try {
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());

            if(userInfo.isPresent()) {
                EventLike eventLike = eventService.saveEventLike(userInfo.get().getId(), paramMap.get("eventId"));
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "delete favorite event", description = "이벤트 좋아요(즐겨찾기) 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED(권한 없음)")
    })
    @Parameters({
            @Parameter(name = "eventId", description = "이벤트 ID", example = "1", required = true),
    })
    @DeleteMapping("/likes")
    public ResponseEntity<?> deleteFavoriteEvent(@RequestBody Map<String, Long> paramMap, Authentication authentication) {
        try {
            User tokenUser = (User) authentication.getPrincipal();
            Optional<com.tworld.celebring.user.model.User> userInfo = loginService.getUserInfoByOauthId(tokenUser.getUsername());

            if(userInfo.isPresent()) {
                eventService.delEventLike(userInfo.get().getId(), paramMap.get("eventId"));
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
