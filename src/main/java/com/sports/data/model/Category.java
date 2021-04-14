package com.sports.data.model;

import lombok.Data;

@Data
public class Category {

    private int id;
    private String name;
    private String slug;
    private Sport sport;
    private String flag;
}
