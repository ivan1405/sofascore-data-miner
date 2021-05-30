package com.sports.data.service;

import com.sports.data.model.sofascore.event.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventDataMinerService {

    void mineEventsData(int daysOffset);

    List<Event> getEventsData(boolean finished);

    List<Event> getEventsDataByDate(boolean finished, LocalDate date);

    Event getEventData(int eventId);
}
