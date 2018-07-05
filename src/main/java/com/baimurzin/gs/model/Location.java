/*
 * Copyright 2018 by HireRight, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HireRight, Inc. Use is subject to license terms.
 *
 * History:
 * V.Baimurzin 2018-07-05 Created
 */
package com.baimurzin.gs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LOCATIONS")
@Data
public class Location {

    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Integer locationId;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
