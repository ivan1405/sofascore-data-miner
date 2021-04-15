package com.sports.data.crud.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
@Data
public class Player {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "name_code")
    private String nameCode;

    @Column(name = "slug")
    private String slug;

    @Column(name = "gender")
    private String gender;

    @Column(name = "sport")
    private String sport;

    @Column(name = "residence")
    private String residence;

    @Column(name = "birthplace")
    private String birthplace;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private String weight;

    @Column(name = "plays")
    private String plays;

    @Column(name = "turned_pro")
    private String turnedPro;

    @Column(name = "prize_current")
    private Integer prizeCurrent;

    @Column(name = "prize_total")
    private Integer prizeTotal;

    @Column(name = "birthDate_timestamp")
    private Integer birthDateTimestamp;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "previous_ranking")
    private Integer previousRanking;

    @Column(name = "best_ranking")
    private Integer bestRanking;

    @Column(name = "tournaments_played")
    private Integer tournamentsPlayed;

    @Column(name = "points")
    private Integer points;

    @Column(name = "national")
    private boolean national;

    @Column(name = "country")
    private String country;

}
