package com.sports.data.model.sofascore.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sports.data.model.Player;
import com.sports.data.model.sofascore.team.Team;
import lombok.Data;

@Data
public class Event {

    private Integer id;
    private String slug;
    private String date;
    private String firstToServe;
    private String winner;
    private Status status;
    @JsonIgnore
    private Integer winnerCode;
    @JsonIgnore
    private Team homeTeam;
    @JsonIgnore
    private Team awayTeam;
    private Player homePlayer;
    private Player awayPlayer;
    private Score homeScore;
    private Score awayScore;
    private String groundType;

    public boolean isDoubles() {
        return homeTeam.getSubTeams() != null && !homeTeam.getSubTeams().isEmpty();
    }

}