package com.sports.data.service;

import com.sports.data.model.Ranking;
import com.sports.data.model.Team;

import java.util.List;

public interface PlayerDataMinerService {

    List<Ranking> getRankings();

    Team getTeamDetail(Integer teamId);

}
