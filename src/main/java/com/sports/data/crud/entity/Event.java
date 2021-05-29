package com.sports.data.crud.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "event_title")
    private String matchTitle;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_player", referencedColumnName = "player_id")
    private Player homePlayer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "away_player", referencedColumnName = "player_id")
    private Player awayPlayer;

    @Column(name = "winner")
    private String winner;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "date")
    private String date;

    @Column(name = "first_to_serve")
    private String firstToServe;

    @Column(name = "home_sets_score")
    private Integer homeSetsScore;

    @Column(name = "away_sets_score")
    private Integer awaySetsScore;

    @Column(name = "total_sets")
    private Integer totalSets;

    @Column(name = "ground_type")
    private String groundType;

    @Column(name = "home_current")
    private Integer homeCurrent;

    @Column(name = "home_period1")
    private Integer homePeriod1;

    @Column(name = "home_period2")
    private Integer homePeriod2;

    @Column(name = "home_period3")
    private Integer homePeriod3;

    @Column(name = "home_period4")
    private Integer homePeriod4;

    @Column(name = "home_period5")
    private Integer homePeriod5;

    @Column(name = "home_period6")
    private Integer homePeriod6;

    @Column(name = "home_period1TieBreak")
    private Integer homePeriod1TieBreak;

    @Column(name = "home_period2TieBreak")
    private Integer homePeriod2TieBreak;

    @Column(name = "home_period3TieBreak")
    private Integer homePeriod3TieBreak;

    @Column(name = "home_period4TieBreak")
    private Integer homePeriod4TieBreak;

    @Column(name = "home_period5TieBreak")
    private Integer homePeriod5TieBreak;

    @Column(name = "home_period6TieBreak")
    private Integer homePeriod6TieBreak;

    @Column(name = "away_current")
    private Integer awayCurrent;

    @Column(name = "away_period1")
    private Integer awayPeriod1;

    @Column(name = "away_period2")
    private Integer awayPeriod2;

    @Column(name = "away_period3")
    private Integer awayPeriod3;

    @Column(name = "away_period4")
    private Integer awayPeriod4;

    @Column(name = "away_period5")
    private Integer awayPeriod5;

    @Column(name = "away_period6")
    private Integer awayPeriod6;

    @Column(name = "away_period1TieBreak")
    private Integer awayPeriod1TieBreak;

    @Column(name = "away_period2TieBreak")
    private Integer awayPeriod2TieBreak;

    @Column(name = "away_period3TieBreak")
    private Integer awayPeriod3TieBreak;

    @Column(name = "away_period4TieBreak")
    private Integer awayPeriod4TieBreak;

    @Column(name = "away_period5TieBreak")
    private Integer awayPeriod5TieBreak;

    @Column(name = "away_period6TieBreak")
    private Integer awayPeriod6TieBreak;

}
