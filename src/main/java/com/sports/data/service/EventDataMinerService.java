package com.sports.data.service;

import com.sports.data.model.sofascore.event.Event;

import java.util.List;

public interface EventDataMinerService {

    List<Event> getEventsByDay(String date);
}
