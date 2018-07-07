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
@Table(name = "CATEGORIES")
@Data
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long categoryId;

    private String name;

    @OneToMany(mappedBy = "productId",  targetEntity = Product.class, cascade = CascadeType.ALL)
    private List<Product> products;


}
