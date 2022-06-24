package com.parser.kladr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@AllArgsConstructor
@Table(name = "street")
public class Street {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "socr")
    private String socr;

    @Column(name = "code")
    private String code;

    @Column(name = "index")
    private String index;

    @Column(name = "gninmb")
    private String gninmb;

    @Column(name = "uno")
    private String uno;

    @Column(name = "ocatd")
    private String ocatd;

    public Street() {

    }
}
