package com.sports.data.crud.repository;

import com.sports.data.crud.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    List<Event> findEventsByDate(String date);

    Event findByEventId(String eventId);

}
