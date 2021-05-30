package com.sports.data.crud.repository;

import com.sports.data.crud.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    Event findByEventIdAndDate(Integer eventId, String date);

    List<Event> findAllByDescription(String description);

    List<Event> findAllByDescriptionAndDate(String description, String date);

    List<Event> findAllByDateAndDescriptionNot(String date, String description);

    List<Event> findAllByDescriptionNot(String description);

    List<Event> findAllByDate(String date);

    Event findByEventId(Integer eventId);
}
