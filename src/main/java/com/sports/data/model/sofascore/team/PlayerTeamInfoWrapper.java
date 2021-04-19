package com.sports.data.model.sofascore.team;

import lombok.Data;

@Data
public class PlayerTeamInfoWrapper {

    private Ranking rankingInfo;
    private Team teamInfo;

    public PlayerTeamInfoWrapper(Ranking rankingInfo, Team teamInfo) {
        this.rankingInfo = rankingInfo;
        this.teamInfo = teamInfo;
    }
}
