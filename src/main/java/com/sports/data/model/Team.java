package com.sports.data.model;

import lombok.Data;

@Data
public class Team {

    private Integer id;
    private String name;
    private String shortName;
    private String fullName;
    private String slug;
    private String gender;
    private Sport sport;
    private Category category;
    private PlayerTeamInfo playerTeamInfo;
    private String nameCode;
    private Integer ranking;
    private boolean national;
    private Country country;

}
