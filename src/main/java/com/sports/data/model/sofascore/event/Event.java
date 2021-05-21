package com.sports.data.model.sofascore.event;

import com.sports.data.model.sofascore.team.Team;
import lombok.Data;

@Data
public class Event {

    private Integer id;
    private String slug;
    private String firstToServe;
    private Status status;
    private Integer winnerCode;
    private String winner;
    private String date;
    private Team homeTeam;
    private Team awayTeam;
    private Score homeScore;
    private Score awayScore;
    private String groundType;

}
