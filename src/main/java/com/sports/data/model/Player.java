package com.sports.data.model;

import lombok.Data;

@Data
public class Player {

    private Integer id;
    private String name;
    private String shortName;
    private String fullName;
    private String nameCode;
    private String slug;
    private String gender;
    private String sport;
    private String residence;
    private String birthplace;
    private String height;
    private String weight;
    private String plays;
    private String turnedPro;
    private Integer prizeCurrent;
    private Integer prizeTotal;
    private Integer birthDateTimestamp;
    private Integer ranking;
    private Integer previousRanking;
    private Integer bestRanking;
    private Integer tournamentsPlayed;
    private Integer points;
    private boolean national;
    private String country;
}
