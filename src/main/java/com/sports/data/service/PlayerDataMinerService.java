package com.sports.data.service;

import com.sports.data.model.Player;
import com.sports.data.model.sofascore.team.Ranking;
import com.sports.data.model.sofascore.team.Team;

import java.util.List;

public interface PlayerDataMinerService {

    List<Ranking> getRankings();

    Team getTeamDetail(Integer teamId);

    void minePlayersData();

    List<Player> findAllPlayers();

    Player findPlayerById(Integer id);

}
