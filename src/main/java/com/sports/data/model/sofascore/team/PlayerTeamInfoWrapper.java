package com.sports.data.model.sofascore.team;

import lombok.Data;

@Data
public class PlayerTeamInfoWrapper {

    private TeamFullInfo teamFullInfoInfo;
    private Team teamInfo;

    public PlayerTeamInfoWrapper(TeamFullInfo teamFullInfoInfo, Team teamInfo) {
        this.teamFullInfoInfo = teamFullInfoInfo;
        this.teamInfo = teamInfo;
    }
}
