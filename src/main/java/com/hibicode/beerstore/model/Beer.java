package com.hibicode.beerstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "beer")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Beer implements Serializable {

    @Id
    @SequenceGenerator(name = "beer_seq", sequenceName = "beer_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_seq")
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private BeerType type;
    private BigDecimal volume;

}
