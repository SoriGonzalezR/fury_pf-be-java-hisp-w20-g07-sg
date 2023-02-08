package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "seccion")
public class Seccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "temperatura_min")
    private double temperaturaMin;
    @Column(name = "temperatura_max")
    private double temperaturaMax;
    @Column(name = "capacidad_maxima_lotes")
    private int capacidadMaximaLotes;

    //mapear
    private WareHouse wareHouse;

    //mapear
    private Categoria categoria;

    //mapear
    private List<Lote> lotes;

}
