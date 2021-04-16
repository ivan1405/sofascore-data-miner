package com.sports.data.controller;

import com.sports.data.model.sofascore.event.Event;
import com.sports.data.service.EventDataMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsDataMinerController {

    private final EventDataMinerService eventDataMinerService;

    @Autowired
    public EventsDataMinerController(final EventDataMinerService eventDataMinerService) {
        this.eventDataMinerService = eventDataMinerService;
    }


    @RequestMapping(value = "/data-miner/events/{date}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEvents(@PathVariable("date") String date) {
        return new ResponseEntity<>(eventDataMinerService.getEventsByDay(date), HttpStatus.OK);
    }
}
