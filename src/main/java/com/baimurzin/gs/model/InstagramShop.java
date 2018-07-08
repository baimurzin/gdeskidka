package com.baimurzin.gs.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("instagram")
@Data
@EqualsAndHashCode(callSuper = true)
public class InstagramShop extends Shop {

}
