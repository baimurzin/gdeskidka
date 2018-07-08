package com.baimurzin.gs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Альбом создается по умолчанию для каждого товара.
 * По умолчанию ставится стоковая фотка.
 * У товара может быть много фоток и одна галвная - это альбом.
 */
@Entity
@Table(name = "albums")
@Data
public class Album {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @OneToMany(targetEntity = Photo.class, mappedBy = "id")
    private List<Photo> photos;
}
