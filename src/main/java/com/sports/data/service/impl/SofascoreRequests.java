package com.sports.data.service.impl;

import com.google.gson.Gson;
import com.sports.data.model.sofascore.event.Event;
import com.sports.data.model.sofascore.event.EventsList;
import com.sports.data.model.sofascore.team.Ranking;
import com.sports.data.model.sofascore.team.RankingList;
import com.sports.data.model.sofascore.team.TeamFullInfo;
import com.sports.data.model.sofascore.team.TeamWrapper;
import com.sports.data.shared.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.sports.data.shared.Constants.buildSofascoreEndpoint;

@Slf4j
public class SofascoreRequests {

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final Gson gson = new Gson();

    List<Event> getSofascoreEventsByDay(String date) {
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

    TeamFullInfo getSofascoreTeamDetail(Integer teamId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(buildSofascoreEndpoint(Constants.SOFASCORE_API_GET_TEAM) + "/" + teamId))
                .setHeader("User-Agent", Constants.SOFASCORE_USER_AGENT)
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            TeamWrapper teamWrapper = gson.fromJson(response.body(), TeamWrapper.class);
            Ranking ranking = this.getSofascoreTeamRanking(teamId);
            return new TeamFullInfo(teamWrapper.getTeam(), ranking);
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private Ranking getSofascoreTeamRanking(Integer teamId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(buildSofascoreEndpoint(Constants.SOFASCORE_API_GET_TEAM) + "/" + teamId + "/rankings"))
                .setHeader("User-Agent", Constants.SOFASCORE_USER_AGENT)
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            RankingList rankings = gson.fromJson(response.body(), RankingList.class);
            return rankings != null && rankings.getRankings() != null ? rankings.getRankings().get(0) : new Ranking();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
