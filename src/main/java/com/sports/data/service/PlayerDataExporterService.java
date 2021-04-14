package com.sports.data.service;

import com.sports.data.crud.entity.Player;

public interface PlayerDataExporterService {

    Player savePlayerInformation(Player player);

    Player exportPlayerInformation(Integer playerId);

}
