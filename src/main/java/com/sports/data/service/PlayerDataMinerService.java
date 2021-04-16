package com.sports.data.service;

import com.sports.data.model.Player;
import com.sports.data.model.sofascore.Ranking;
import com.sports.data.model.sofascore.Team;

import java.util.List;

public interface PlayerDataMinerService {

    List<Ranking> getRankings();

    Team getTeamDetail(Integer teamId);

    void minePlayersData();

    List<Player> findAllPlayers();

    Player findPlayerById(Integer id);

}
