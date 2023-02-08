package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orden_entrante")
public class OrdenEntrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int numero;

    private LocalDate fecha;

    //mappear
    private List<Lote> lotes;
}
