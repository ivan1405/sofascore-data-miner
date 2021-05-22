package com.sports.data.model.sofascore.team;

import lombok.Data;

import java.util.List;

@Data
public class Team {

    private Integer id;
    private String name;
    private String shortName;
    private String fullName;
    private String nameCode;
    private String slug;
    private String gender;
    private Sport sport;
    private List<Team> subTeams;
    private Category category;
    private PlayerTeamInfo playerTeamInfo;
    private Integer ranking;
    private boolean national;
    private Country country;

}
