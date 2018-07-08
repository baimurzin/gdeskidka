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
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("customer")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {

    //brand
    @Column(name = "shown_name")
    private String shownName;

    private List<Shop> shops;

}
