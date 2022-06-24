package com.parser.kladr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@Table(name = "value")
public class Value {

    @Id
    @SequenceGenerator(name = "SEQ_VALUE", sequenceName = "SEQ_VALUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VALUE")
    @Column(name = "id")
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "streets")
    private String streets;

    public Value() {
    }
}
