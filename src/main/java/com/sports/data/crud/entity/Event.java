package com.sports.data.crud.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
@Data
public class Event {

    @Id
    @Column(name = "id")
    private Integer id;
}
