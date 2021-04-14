package com.sports.data.controller;

import com.sports.data.service.PlayerDataMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpClient;

@RestController
public class PlayerDataExporterController {

    private final PlayerDataMinerService playerDataMinerService;

    @Autowired
    public PlayerDataExporterController(final PlayerDataMinerService playerDataMinerService) {
        this.playerDataMinerService = playerDataMinerService;
    }
}
