package com.sports.data.service.impl;

import com.google.gson.Gson;
import com.sports.data.crud.entity.Player;
import com.sports.data.crud.repository.PlayerRepository;
import com.sports.data.mapper.PlayerMapper;
import com.sports.data.model.Ranking;
import com.sports.data.model.RankingList;
import com.sports.data.model.Team;
import com.sports.data.model.TeamWrapper;
import com.sports.data.service.PlayerDataMinerService;
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
import java.util.concurrent.atomic.AtomicInteger;

import static com.sports.data.shared.Constants.buildSofascoreEndpoint;

@Service
@Slf4j
public class SofascorePlayerDataMiner implements PlayerDataMinerService {

    private final HttpClient httpClient;
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    private final Gson gson = new Gson();

    @Autowired
    public SofascorePlayerDataMiner(final HttpClient httpClient, final PlayerRepository playerRepository,
                                    final PlayerMapper playerMapper) {
        this.httpClient = httpClient;
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public List<Ranking> getRankings() {
        log.info("Getting full list of ranking");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(buildSofascoreEndpoint(Constants.SOFASCORE_API_RANKINGS_TENNIS)))
                .setHeader("User-Agent", Constants.SOFASCORE_USER_AGENT)
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
                .uri(URI.create(buildSofascoreEndpoint(Constants.SOFASCORE_API_GET_TEAM) + "/" + teamId))
                .setHeader("User-Agent", Constants.SOFASCORE_USER_AGENT)
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            TeamWrapper teamWrapper = gson.fromJson(response.body(), TeamWrapper.class);
            log.info("OK - Delivering data");
            return teamWrapper.getTeam();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void minePlayersData() {
        log.info("Starting with the data mining...");
        List<Ranking> rankings = this.getRankings();
        AtomicInteger playersSaved = new AtomicInteger();
        rankings.forEach(ranking -> {
            Team team = getTeamDetail(ranking.getTeam().getId());
            Player player = playerMapper.map(team, Player.class);
            playerRepository.save(player);
            playersSaved.getAndIncrement();
        });
        log.info("{} players have been saved!", playersSaved);
    }

}
