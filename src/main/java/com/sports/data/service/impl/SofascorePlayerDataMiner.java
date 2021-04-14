package com.sports.data.service.impl;

import com.google.gson.Gson;
import com.sports.data.model.Ranking;
import com.sports.data.model.RankingList;
import com.sports.data.model.Team;
import com.sports.data.model.TeamWrapper;
import com.sports.data.service.PlayerDataMinerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Slf4j
public class SofascorePlayerDataMiner implements PlayerDataMinerService {

    private final HttpClient httpClient;
    private final Gson gson = new Gson();

    @Autowired
    public SofascorePlayerDataMiner(final HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    @Override
    public List<Ranking> getRankings() {
        log.info("Getting full list of ranking");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.sofascore.com/api/v1/rankings/type/5"))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            RankingList rankings = gson.fromJson(response.body(), RankingList.class);
            log.info("OK - {} results found!", rankings.getRankings().size());
            return rankings.getRankings();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Team getTeamDetail(Integer teamId) {
        log.info("Getting team detail");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.sofascore.com/api/v1/team/" + teamId))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            TeamWrapper teamWrapper = gson.fromJson(response.body(), TeamWrapper.class);
            System.out.println(teamWrapper.getTeam().getName());
            log.info("OK - Delivering data");
            return teamWrapper.getTeam();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
