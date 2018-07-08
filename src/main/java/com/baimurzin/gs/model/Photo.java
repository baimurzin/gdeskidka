package com.baimurzin.gs.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "photos")
@Data
public class Photo {

    @Id
    @GeneratedValue
    private Long id;
    private boolean main;
    private String baseUrl;
    private String description;
}
