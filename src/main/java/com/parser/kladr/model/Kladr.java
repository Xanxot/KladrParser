package com.parser.kladr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@Table(name = "kladr")
public class Kladr {

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

    @Column(name = "status")
    private String status;

    public Kladr() {

    }
}
