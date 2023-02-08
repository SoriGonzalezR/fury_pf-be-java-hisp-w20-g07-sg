package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lote")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cantidad_inicial")
    private int cantidadInicial;
    @Column(name = "cantidad_actual")
    private int cantidadActual;

    @Column(name = "temperatura_minima")
    private double temperaturaMinima;

    //mapear
    private OrdenEntrante ordenEntrante;

    //mappear
    private Producto producto;
}
