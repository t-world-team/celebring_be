package com.tworld.celebring.event.repository;

import com.tworld.celebring.event.model.EventCeleb;
import com.tworld.celebring.event.model.EventCelebId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCelebRepository extends JpaRepository<EventCeleb, EventCelebId> {
}
