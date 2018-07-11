package com.baimurzin.gs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue
    private Long id;


}
