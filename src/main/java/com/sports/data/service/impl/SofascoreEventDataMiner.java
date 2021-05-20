package com.sports.data.service.impl;

import com.google.gson.Gson;
import com.sports.data.crud.entity.Player;
import com.sports.data.crud.repository.EventRepository;
import com.sports.data.crud.repository.PlayerRepository;
import com.sports.data.mapper.EventMapper;
import com.sports.data.model.sofascore.event.Event;
import com.sports.data.model.sofascore.event.EventsList;
import com.sports.data.service.EventDataMinerService;
import com.sports.data.shared.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sports.data.shared.Constants.buildSofascoreEndpoint;

@Service
@Slf4j
public class SofascoreEventDataMiner implements EventDataMinerService {

    private final HttpClient httpClient;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final PlayerRepository playerRepository;

    private final Gson gson = new Gson();

    @Autowired
    public SofascoreEventDataMiner(final HttpClient httpClient, final EventRepository eventRepository,
                                   final PlayerRepository playerRepository, final EventMapper eventMapper) {
        this.httpClient = httpClient;
        this.eventRepository = eventRepository;
        this.playerRepository = playerRepository;
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

    @Override
    public void mineEventsData(int daysOffset) {
        log.info("Starting with the events data mining...");
        StopWatch watch = new StopWatch();
        watch.start();
        LocalDate date = LocalDate.now();
        AtomicInteger eventsSaved = new AtomicInteger();

        while (daysOffset > 0) {
            List<Event> events = getEventsByDay(date.toString());
            LocalDate finalDate = date;
            events.forEach(event -> {
                if ("Ended".equals(event.getStatus().getDescription())) {
                    com.sports.data.crud.entity.Event eventEntity =
                            eventMapper.map(event, com.sports.data.crud.entity.Event.class);

                    Player homePlayer = playerRepository.findPlayerById(event.getHomeTeam().getId());
                    Player awayPlayer = playerRepository.findPlayerById(event.getAwayTeam().getId());

                    if (homePlayer != null) {
                        eventEntity.setHomePlayer(homePlayer);
                    }
                    if (awayPlayer != null) {
                        eventEntity.setAwayPlayer(awayPlayer);
                    }

                    eventEntity.setDate(finalDate.toString());
                    eventRepository.save(eventEntity);
                    eventsSaved.getAndIncrement();
                }
            });
            date = date.minusDays(1);
            daysOffset--;
        }
        watch.stop();
        log.info("{} events have been exported in {} seconds!", eventsSaved, watch.getTime(TimeUnit.SECONDS));
    }

    @Override
    public List<Event> getEventsData(int daysOffset) {
        log.info("Getting events data...");
        Iterable<com.sports.data.crud.entity.Event> eventsEntity = eventRepository.findAll();
        return eventMapper.mapAsList(eventsEntity, Event.class);
    }
}
