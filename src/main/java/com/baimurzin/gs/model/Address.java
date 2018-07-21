package com.baimurzin.gs.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@RequiredArgsConstructor
@Data
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="address")
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private Long id;


}
