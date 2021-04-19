package com.sports.data.model.sofascore.team;

import lombok.Data;

@Data
public class PlayerTeamInfo {

    private Integer id;
    private String residence;
    private String birthplace;
    private String height;
    private String weight;
    private String plays;
    private String turnedPro;
    private Integer prizeCurrent;
    private Integer prizeTotal;
    private Integer birthDateTimestamp;

}
