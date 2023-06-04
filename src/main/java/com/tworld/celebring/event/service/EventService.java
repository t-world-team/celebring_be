package com.tworld.celebring.event.service;

import com.tworld.celebring.common.dto.PageIndex;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Page<EventListDto> getCurrentEventList(Pageable pageable) {
        List<EventListDto> content = eventRepository.findCurrentEvents(pageable);
        Long count = eventRepository.findCurrentEventsCount();
        return new PageImpl<>(content, pageable, count);
    }

    public Page<EventListDto> getCelebEventList(Long celebId, int page, int size) {

        List<EventListDto> currentList = eventRepository.findCurrentEventsByCeleb(celebId);
        List<EventListDto> upcomingList = eventRepository.findUpcomingEventsByCeleb(celebId);
        List<EventListDto> pastList = eventRepository.findPastEventsByCeleb(celebId);

        List<EventListDto> eventList = Stream.concat(currentList.stream(),
                Stream.concat(upcomingList.stream(), pastList.stream()))
                .collect(Collectors.toList());

        List<EventListDto> content = (List<EventListDto>) PageIndex.getPage(eventList, page, size);
        Long count = eventRepository.findEventsCountByCeleb(celebId);
        return new PageImpl<>(content, PageRequest.of(page, size), count);
    }

}
