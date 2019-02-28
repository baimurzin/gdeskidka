package com.baimurzin.gs.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shops")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="shop")
@Data
@Builder
@EqualsAndHashCode
public class Shop {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(targetEntity = Address.class)
    private Address address;

    @OneToOne(targetEntity = Photo.class)
    private Photo photo;

    @OneToMany(targetEntity = Product.class, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Product> products;
}
