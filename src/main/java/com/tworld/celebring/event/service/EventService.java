package com.tworld.celebring.event.service;

import com.tworld.celebring.celeb.model.ViewCeleb;
import com.tworld.celebring.common.dto.PageIndex;
import com.tworld.celebring.event.dto.EventDetailDto;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.model.Event;
import com.tworld.celebring.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    /**
     * 현재 진행중인 이벤트 목록
     * @param pageable
     * @return
     */
    public Page<EventListDto> getCurrentEventList(Pageable pageable) {
        List<EventListDto> content = eventRepository.findCurrentEvents(pageable);
        getCelebList(content);

        Long count = eventRepository.findCurrentEventsCount();
        return new PageImpl<>(content, pageable, count);
    }

    /**
     * 셀럽의 이벤트 목록(현재-예정-지난)
     * @param celebId
     * @param page
     * @param size
     * @return
     */
    public Page<EventListDto> getCelebEventList(Long celebId, int page, int size) {

        List<EventListDto> currentList = eventRepository.findCurrentEventsByCeleb(celebId);
        List<EventListDto> upcomingList = eventRepository.findUpcomingEventsByCeleb(celebId);
        List<EventListDto> pastList = eventRepository.findPastEventsByCeleb(celebId);

        List<EventListDto> eventList = Stream.concat(currentList.stream(),
                Stream.concat(upcomingList.stream(), pastList.stream()))
                .collect(Collectors.toList());

        @SuppressWarnings("unchecked") List<EventListDto> content = (List<EventListDto>) PageIndex.getPage(eventList, page, size);
        getCelebList(content);

        Long count = eventRepository.findEventsCountByCeleb(celebId);
        return new PageImpl<>(content, PageRequest.of(page, size), count);
    }

    /**
     * 이벤트의 셀럽 목록
     * @param content
     */
    private void getCelebList(List<EventListDto> content) {
        for (EventListDto list: content) {
            List<ViewCeleb> viewCelebList = eventRepository.findCelebInfoByEvent(list.getEventId());
            List<String> celebList = new ArrayList<>();
            for (ViewCeleb celeb: viewCelebList) {
                String name = celeb.getName();
                if (celeb.getGroupId() != null) name += "(" + celeb.getGroupName() + ")";
                celebList.add(name);
            }
            list.setCeleb(celebList);
        }
    }

    /**
     * 이벤트 상세 정보
     * @param eventId
     * @param userId
     * @return
     */
    public EventDetailDto getEventDetail(Long eventId, Long userId) {
        return eventRepository.findEventDetail(eventId, userId);
    }

}
