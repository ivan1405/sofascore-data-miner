package com.sports.data.model;

import lombok.Data;

@Data
public class Ranking {

    private Integer id;
    private Team team;
    private Integer tournamentsPlayed;
    private String rowName;
    private Integer ranking;
    private Integer points;
    private Integer previousRanking;
    private Integer bestRanking;

}
