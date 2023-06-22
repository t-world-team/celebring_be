package com.tworld.celebring.event.repository;

import com.tworld.celebring.event.model.EventLike;
import com.tworld.celebring.event.model.EventLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLikeRepository extends JpaRepository<EventLike, EventLikeId> {

   <T extends EventLike> T save(T eventLike);
   void delete(EventLike eventLike);
}
