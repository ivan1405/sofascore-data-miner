package com.sports.data.crud.repository;

import com.sports.data.crud.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    Event findByEventIdAndDate(String eventId, String date);
}
