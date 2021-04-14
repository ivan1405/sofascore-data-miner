package com.sports.data.controller;

import com.sports.data.model.Ranking;
import com.sports.data.model.Team;
import com.sports.data.service.PlayerDataMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpClient;
import java.util.List;

@RestController
public class PlayerDataMinerController {

    private final PlayerDataMinerService playerDataMinerService;


    @Autowired
    public PlayerDataMinerController(final PlayerDataMinerService playerDataMinerService,
                                     final HttpClient httpClient) {
        this.playerDataMinerService = playerDataMinerService;
    }

    @RequestMapping(value = "/ranking", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<Ranking>> getPlayers() {
        return new ResponseEntity<>(playerDataMinerService.getRankings(), HttpStatus.OK);

    }

    @RequestMapping(value = "/ranking/{teamId}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<Team> getPlayerInfo(@PathVariable("teamId") Integer teamId) {
        return new ResponseEntity<>(playerDataMinerService.getTeamDetail(teamId), HttpStatus.OK);
    }
}
