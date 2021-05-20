package com.sports.data.controller;

import com.sports.data.model.sofascore.event.Event;
import com.sports.data.service.EventDataMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/data-miner/events/start", produces = {"application/json"}, method = RequestMethod.GET)
    public void mineEventsData(@RequestParam(required = false, defaultValue = "30") Integer daysOffset) {
        eventDataMinerService.mineEventsData(daysOffset);
    }

    @RequestMapping(value = "/data-miner/events", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEventsData(@RequestParam(required = false, defaultValue = "30") Integer daysOffset) {
        return new ResponseEntity<>(eventDataMinerService.getEventsData(daysOffset), HttpStatus.OK);
    }
}
