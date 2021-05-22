package com.sports.data.service;

import com.sports.data.model.Player;

import java.util.List;

public interface PlayerDataMinerService {

    List<Player> findAllPlayers();

    Player findPlayerById(Integer id);

    void importPlayer(Integer teamId);

}
