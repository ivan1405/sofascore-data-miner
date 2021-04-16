package com.sports.data.model.sofascore.event;

import lombok.Data;

@Data
public class Score {

    private Integer current;
    private Integer period1;
    private Integer period2;
    private Integer period3;
    private Integer period4;
    private Integer period5;
    private Integer period6;
    private Integer period1TieBreak;
    private Integer period2TieBreak;
    private Integer period3TieBreak;
    private Integer period4TieBreak;
    private Integer period5TieBreak;
    private Integer period6TieBreak;
}
