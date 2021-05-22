package com.sports.data.controller;

import com.sports.data.model.Player;
import com.sports.data.service.PlayerDataMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerDataMinerController {

    private final PlayerDataMinerService playerDataMinerService;

    @Autowired
    public PlayerDataMinerController(final PlayerDataMinerService playerDataMinerService) {
        this.playerDataMinerService = playerDataMinerService;
    }

    @RequestMapping(value = "/data-miner/players", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<Player>> getPlayers() {
        return new ResponseEntity<>(playerDataMinerService.findAllPlayers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/data-miner/players/{playerId}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<Player> getPlayers(@PathVariable("playerId") Integer playerId) {
        return new ResponseEntity<>(playerDataMinerService.findPlayerById(playerId), HttpStatus.OK);
    }
}
