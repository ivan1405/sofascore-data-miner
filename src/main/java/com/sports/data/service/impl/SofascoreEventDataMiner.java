package com.sports.data.service.impl;

import com.sports.data.crud.repository.EventRepository;
import com.sports.data.crud.repository.PlayerRepository;
import com.sports.data.mapper.EventMapper;
import com.sports.data.model.sofascore.event.Event;
import com.sports.data.service.EventDataMinerService;
import com.sports.data.service.PlayerDataMinerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class SofascoreEventDataMiner extends SofascoreRequests implements EventDataMinerService {

    private final PlayerDataMinerService playerDataMinerService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final PlayerRepository playerRepository;

    @Autowired
    public SofascoreEventDataMiner(final PlayerDataMinerService playerDataMinerService,
                                   final EventRepository eventRepository, final PlayerRepository playerRepository,
                                   final EventMapper eventMapper) {
        this.playerDataMinerService = playerDataMinerService;
        this.eventRepository = eventRepository;
        this.playerRepository = playerRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public void mineEventsData(int daysOffset) {
        log.info("Starting with the events data mining...");
        StopWatch watch = new StopWatch();
        watch.start();
        LocalDate date = LocalDate.now();
        while (daysOffset > 0) {
            saveEventsByDate(date, false);
            date = date.minusDays(1);
            daysOffset--;
        }
        watch.stop();
        log.info("Events have been exported in {} seconds!", watch.getTime(TimeUnit.SECONDS));
    }

    @Override
    public List<Event> getEventsData(boolean finished) {
        log.info("Getting events...");
        Iterable<com.sports.data.crud.entity.Event> eventsEntity = finished ?
                eventRepository.findAllByDescription("Ended") : eventRepository.findAllByDescriptionNot("Ended");
        return eventMapper.mapAsList(eventsEntity, Event.class);
    }


    @Override
    public List<Event> getEventsDataByDate(boolean finished, LocalDate date) {
        log.info("Getting events...");
        Iterable<com.sports.data.crud.entity.Event> eventsEntity = finished ?
                eventRepository.findAllByDescriptionAndDate("Ended", date.toString()) :
                eventRepository.findAllByDateAndDescriptionNot(date.toString(), "Ended");
        return eventMapper.mapAsList(eventsEntity, Event.class);
    }

    @Override
    public Event getEventData(int eventId) {
        log.info("Getting information for event {}", eventId);
        com.sports.data.crud.entity.Event event = eventRepository.findByEventId(eventId);
        return eventMapper.map(event, Event.class);
    }

    /**
     * This cronjob is used to mine the new events of each day
     */
    @Scheduled(cron = "0 50 22 * * *")
    private void mineEventsOfTheDay() {
        log.info("Starting with the events data mining of the day...");
        StopWatch watch = new StopWatch();
        watch.start();
        saveEventsByDate(LocalDate.now(), false);
        watch.stop();
        log.info("Scheduled events have been exported in {} seconds!", watch.getTime(TimeUnit.SECONDS));
    }

    /**
     * This cronjob is used to mine the new events of the next day
     */
    @Scheduled(cron = "0 55 22 * * *")
    private void mineEventsOfTheNextDay() {
        log.info("Starting with the events data mining of the day...");
        StopWatch watch = new StopWatch();
        watch.start();
        LocalDate date = LocalDate.now();
        saveEventsByDate(date.plusDays(1), true);
        watch.stop();
        log.info("Scheduled events for next day have been exported in {} seconds!", watch.getTime(TimeUnit.SECONDS));
    }


    /**
     * This method searches all the events by date and store them into the DB
     *
     * @param date          the date to search the events for
     * @param allowNotEnded flag if not ended events should be considered
     */
    private void saveEventsByDate(LocalDate date, boolean allowNotEnded) {
        List<Event> events = getSofascoreEventsByDay(date.toString());
        AtomicInteger eventsSaved = new AtomicInteger();
        if(!events.isEmpty()){
            events.forEach(event -> {

                // Update players information
                this.updatePlayers(event);

                if (isEventValid(event, allowNotEnded)) {
                    com.sports.data.crud.entity.Event eventById =
                            eventRepository.findByEventIdAndDate(event.getId(), date.toString());
                    // Check if the event has been already stored in the DB
                    if (eventById == null) {
                        com.sports.data.crud.entity.Event eventEntity =
                                eventMapper.map(event, com.sports.data.crud.entity.Event.class);

                        eventEntity.setHomePlayer(playerRepository.findPlayerByPlayerId(event.getHomeTeam().getId()));
                        eventEntity.setAwayPlayer(playerRepository.findPlayerByPlayerId(event.getAwayTeam().getId()));

                        eventEntity.setDate(date.toString());
                        eventRepository.save(eventEntity);
                        eventsSaved.getAndIncrement();
                    } else {
                        log.info("Event {} for date {} has been already mined", event.getSlug(), date.toString());
                    }
                } else {
                    log.info("Event {} does not meet the validations", event.getSlug());
                }
            });
            log.info("{} events have been imported!", eventsSaved);
        }
    }

    /**
     * Update the players information in the database
     * Takes into account if an events is singles or doubles
     *
     * @param event the event
     */
    private void updatePlayers(Event event) {
        // Update home players information
        if (event.getHomeTeam().getSubTeams() != null && !event.getHomeTeam().getSubTeams().isEmpty()) {
            event.getHomeTeam().getSubTeams().forEach(team -> playerDataMinerService.importPlayer(team.getId()));
        } else {
            playerDataMinerService.importPlayer(event.getHomeTeam().getId());
        }
        // Update away players information
        if (event.getAwayTeam().getSubTeams() != null && !event.getAwayTeam().getSubTeams().isEmpty()) {
            event.getAwayTeam().getSubTeams().forEach(team -> playerDataMinerService.importPlayer(team.getId()));
        } else {
            playerDataMinerService.importPlayer(event.getAwayTeam().getId());
        }
    }

    /**
     * Validates the minimum requirements for an event to be valid
     *
     * @param event the event to validate
     * @return true if is valid or false otherwise
     */
    private boolean isEventValid(Event event, boolean allowNotEnded) {
        boolean validity = event.getHomeTeam() != null &&
                !event.getHomeTeam().getSlug().isEmpty() &&
                event.getAwayTeam() != null &&
                !event.getAwayTeam().getSlug().isEmpty() &&
                !event.isDoubles();
        return allowNotEnded ? validity :
                validity &&
                        "Ended".equals(event.getStatus().getDescription()) &&
                        event.getHomeScore() != null &&
                        event.getHomeScore().getCurrent() != null &&
                        event.getAwayScore() != null &&
                        event.getAwayScore().getCurrent() != null;
    }
}
