package com.sports.data.model.sofascore.team;

import lombok.Data;

@Data
public class TeamFullInfo {

    private Integer id;
    private Team team;
    private Ranking ranking;

    public TeamFullInfo(Team team, Ranking ranking) {
        this.team = team;
        this.ranking = ranking;
    }
}
