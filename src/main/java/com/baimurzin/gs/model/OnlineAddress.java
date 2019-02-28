package com.baimurzin.gs.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("online")
@Data
@EqualsAndHashCode(callSuper = true)
public class OnlineAddress extends Address {
    private String address;
}
