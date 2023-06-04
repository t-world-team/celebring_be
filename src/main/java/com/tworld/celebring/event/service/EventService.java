package com.tworld.celebring.event.service;

import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Page<EventListDto> getCurrentEventList(Pageable pageable) {
        List<EventListDto> content = eventRepository.findCurrentEvents(pageable);
        Long count = eventRepository.findCurrentEventsCount();
        return new PageImpl<>(content, pageable, count);
    }

}
