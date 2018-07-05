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
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Integer productId;

    private String name;

    @Column(name = "product_category")
    private Category category;
}
