package com.tworld.celebring.event.service;

import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<EventListDto> getNowEventList() {
        return eventRepository.findNowEvents();
    }
}
