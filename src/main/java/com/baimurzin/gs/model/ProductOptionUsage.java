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
@Table(name = "product_options_usage")
@Data
public class ProductOptionUsage {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Product.class)
    private Product product;

    @ManyToOne(targetEntity = ProductOption.class)
    private ProductOption productOption;


}
