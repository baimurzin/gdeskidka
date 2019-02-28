package com.baimurzin.gs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("offline")
@Data
@EqualsAndHashCode(callSuper = true)
public class OfflineAddress extends Address {

    private String address;

}
