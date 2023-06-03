package com.tworld.celebring.event.controller;

import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "events", description = "이벤트 API")
@RequestMapping("/events")
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @Operation(summary = "get now event list", description = "현재 진행중인 이벤트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("")
    public ResponseEntity<?> getNowEventList() {
        List<EventListDto> list = eventService.getNowEventList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
