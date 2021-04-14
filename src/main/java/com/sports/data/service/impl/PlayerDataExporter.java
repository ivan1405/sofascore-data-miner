package com.sports.data.service.impl;

import com.sports.data.crud.entity.Player;
import com.sports.data.crud.repository.PlayerRepository;
import com.sports.data.service.PlayerDataExporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerDataExporter implements PlayerDataExporterService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerDataExporter(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player savePlayerInformation(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player exportPlayerInformation(Integer playerId) {
        return playerRepository.findPlayerById(playerId);
    }
}
