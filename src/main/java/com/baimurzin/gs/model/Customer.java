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
import java.util.List;

@Entity
@Table(name = "CUSTOMERS")
@Data
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long customerId;

    private String email;

    private String password;

    @Column(name = "shown_name")
    private String shownName;

    @OneToMany(targetEntity = Location.class, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Location> locations;


}
