package com.sports.data.model.sofascore.team;

import lombok.Data;

@Data
public class Ranking {

    private Integer tournamentsPlayed;
    private Integer ranking;
    private Integer points;
    private Integer previousRanking;
    private Integer bestRanking;
}
