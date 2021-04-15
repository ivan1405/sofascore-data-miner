package com.sports.data.service.impl;

import com.google.gson.Gson;
import com.sports.data.crud.entity.Player;
import com.sports.data.crud.repository.PlayerRepository;
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

import static com.sports.data.shared.Constants.buildSofascoreEndpoint;

@Service
@Slf4j
public class SofascorePlayerDataMiner implements PlayerDataMinerService {

    private final HttpClient httpClient;
    private final PlayerRepository playerRepository;

    private final Gson gson = new Gson();

    @Autowired
    public SofascorePlayerDataMiner(final HttpClient httpClient, final PlayerRepository playerRepository) {
        this.httpClient = httpClient;
        this.playerRepository = playerRepository;
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

            // TODO Modificar
            Player player = new Player();
            player.setId(teamWrapper.getTeam().getPlayerTeamInfo().getId());
            player.setName(teamWrapper.getTeam().getName());

            playerRepository.save(player);

            log.info("OK - Delivering data");
            return teamWrapper.getTeam();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
