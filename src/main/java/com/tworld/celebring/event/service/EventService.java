package com.tworld.celebring.event.service;

import com.tworld.celebring.celeb.model.ViewCeleb;
import com.tworld.celebring.common.dto.PageIndex;
import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.event.dto.EventAddDto;
import com.tworld.celebring.event.dto.EventDetailDto;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.model.Event;
import com.tworld.celebring.event.model.EventLike;
import com.tworld.celebring.event.repository.EventJpaRepository;
import com.tworld.celebring.event.repository.EventLikeRepository;
import com.tworld.celebring.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventJpaRepository eventJpaRepository;
    private final EventLikeRepository eventLikeRepository;

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

    /**
     * 이벤트 좋아요
     * @param userId
     * @param eventId
     * @return
     */
    public EventLike saveEventLike(Long userId, Long eventId) {
        return eventLikeRepository.save(new EventLike(userId, eventId));
    }

    /**
     * 이벤트 좋아요 취소
     * @param userId
     * @param eventId
     */
    public void delEventLike(Long userId, Long eventId) {
        eventLikeRepository.delete(new EventLike(userId, eventId));
    }

    /**
     * 이벤트 삭제
     * @param eventId
     * @param userId
     * @return
     */
    public Long delEvent(Long eventId, Long userId) {
        return eventRepository.deleteEvent(eventId, userId);
    }

    public Event saveEvent(Long userId, EventAddDto dto) throws ParseException {
        Event event = Event.builder()
                .name(dto.getEventName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .cafeName(dto.getCafeName())
                .address(dto.getAddress())
                .mapX(dto.getMapX())
                .mapY(dto.getMapY())
                .openingTime(dto.getOpeningTime())
                .sns(dto.getTwitter())
                .userId(userId)
                .build();

        return eventJpaRepository.save(event);
    }
}
