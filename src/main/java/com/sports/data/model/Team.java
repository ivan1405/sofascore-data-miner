package com.sports.data.model;

import lombok.Data;

@Data
public class Team {

    private Integer id;
    private String name;
    private String slug;
    private String shortName;
    private String gender;
    private Sport sport;
    private Category category;

}
