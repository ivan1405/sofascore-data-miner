package com.sports.data.service.impl;

import com.google.gson.Gson;
import com.sports.data.crud.repository.EventRepository;
import com.sports.data.mapper.EventMapper;
import com.sports.data.model.sofascore.event.Event;
import com.sports.data.model.sofascore.event.EventsList;
import com.sports.data.service.EventDataMinerService;
import com.sports.data.shared.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.sports.data.shared.Constants.buildSofascoreEndpoint;

@Service
@Slf4j
public class SofascoreEventDataMiner implements EventDataMinerService {

    private final HttpClient httpClient;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    private final Gson gson = new Gson();

    @Autowired
    public SofascoreEventDataMiner(final HttpClient httpClient, final EventRepository eventRepository,
                                   final EventMapper eventMapper) {
        this.httpClient = httpClient;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<Event> getEventsByDay(String date) {
        log.info("Getting list of events on {}", date);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(buildSofascoreEndpoint(Constants.SOFASCORE_API_GET_EVENTS_DATE) + "/" + date))
                .setHeader("User-Agent", Constants.SOFASCORE_USER_AGENT)
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            EventsList events = gson.fromJson(response.body(), EventsList.class);
            log.info("OK - {} results found!", events.getEvents().size());
            return events.getEvents();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
