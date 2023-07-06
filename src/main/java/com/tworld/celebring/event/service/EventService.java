package com.tworld.celebring.event.service;

import com.tworld.celebring.celeb.model.ViewCeleb;
import com.tworld.celebring.common.dto.PageIndex;
import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.event.dto.*;
import com.tworld.celebring.event.model.Event;
import com.tworld.celebring.event.model.EventCeleb;
import com.tworld.celebring.event.model.EventLike;
import com.tworld.celebring.event.repository.EventCelebRepository;
import com.tworld.celebring.event.repository.EventJpaRepository;
import com.tworld.celebring.event.repository.EventLikeRepository;
import com.tworld.celebring.event.repository.EventRepository;
import jakarta.transaction.Transactional;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventJpaRepository eventJpaRepository;
    private final EventLikeRepository eventLikeRepository;
    private final EventCelebRepository eventCelebRepository;

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
            List<String> imgList = new ArrayList<>();
            for (ViewCeleb celeb: viewCelebList) {
                String name = celeb.getName();
                if (celeb.getGroupId() != null) name += "(" + celeb.getGroupName() + ")";
                celebList.add(name);
                imgList.add(celeb.getProfileImage());
            }
            list.setCeleb(celebList, imgList);
        }
    }

    /**
     * 이벤트 상세 정보
     * @param eventId
     * @param userId
     * @return
     */
    public EventDetailDto getEventDetail(Long eventId, Long userId) {
        EventDetailDto content = eventRepository.findEventDetail(eventId, userId);

        // 셀럽 정보
        List<ViewCeleb> viewCelebList = eventRepository.findCelebInfoByEvent(content.getEventId());
        List<String> celebList = new ArrayList<>();
        for (ViewCeleb celeb: viewCelebList) {
            String name = celeb.getName();
            if (celeb.getGroupId() != null) name += "(" + celeb.getGroupName() + ")";
            celebList.add(name);
        }
        content.setCeleb(celebList);

        return content;
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

    public Long saveEvent(Long userId, EventAddDto dto) throws ParseException {
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

        event = eventJpaRepository.save(event);

        for (Long celebId: dto.getCelebId()) {
            EventCeleb ec = EventCeleb.builder()
                    .eventId(event.getId())
                    .celebId(celebId)
                    .build();
            eventCelebRepository.save(ec);
        }
        return event.getId();
    }

    @Transactional
    public Event updateEvent(Long userId, EventUpdateDto dto) {
        Event event = eventJpaRepository.findById(dto.getId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 이벤트입니다."));

        event.update(dto, userId);
        return eventJpaRepository.save(event);
    }

    /**
     * 등록한 이벤트 목록
     * @param userId
     * @param pageable
     * @return
     */
    public Page<EventListDto> getMyEventList(Long userId, Pageable pageable) {
        List<EventListDto> content = eventRepository.findMyEventList(userId, pageable);
        getCelebList(content);

        Long count = eventRepository.findMyEventsCount(userId);
        return new PageImpl<>(content, pageable, count);
    }

    /**
     * 좋아요한 이벤트 목록
     * @param userId
     * @param pageable
     * @return
     */
    public Page<EventListDto> getFavoriteEventList(Long userId, Pageable pageable) {
        List<EventListDto> content = eventRepository.findFavoriteEventList(userId, pageable);
        getCelebList(content);
        return new PageImpl<>(content, pageable, content.size());
    }

    /**
     * 일 별 셀럽의 이벤트 목록
     * @param celebId
     * @param day
     * @param pageable
     * @return
     */
    public Page<EventListDto> getEventListByCelebAndDay(Long celebId, String day, Pageable pageable) {
        List<EventListDto> content = eventRepository.findEventListByCelebAndDay(celebId, day, pageable);
        getCelebList(content);
        return new PageImpl<>(content, pageable, content.size());
    }

    /**
     * 셀럽의 이벤트 달력
     * @param celebId
     * @param month
     * @return
     */
    public List<EventCalendarDto> getEventCalendar(Long celebId, String month) {
        List<EventCalendarDto> content = new ArrayList<>();

        List<EventListDto> events = eventRepository.findEventByCeleb(celebId);
        Map<String, Integer> map = new HashMap<>();
        for (EventListDto dto : events) {
            List<String> days = eventRepository.findEventDays(dto);
            for (String d: days) {
                if (month.equals(d.substring(0,7))) { // 해당 달인 경우에만 수행
                    if (map.containsKey(d)) {
                        map.put(d, map.get(d) + 1);
                    } else {
                        map.put(d, 1);
                    }
                }
            }
        }

        for (String key: map.keySet()) {
            content.add(EventCalendarDto.builder()
                    .day(key)
                    .count(map.get(key))
                    .build());
        }
        return content;
    }
}
