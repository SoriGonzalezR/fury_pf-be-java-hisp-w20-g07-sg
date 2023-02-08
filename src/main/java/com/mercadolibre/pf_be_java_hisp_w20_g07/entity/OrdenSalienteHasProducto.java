package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orden_saliente_has_producto")
public class OrdenSalienteHasProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int cantidad;

    //mappera
    private Producto producto;

    //mappear
    @Column(name = "orden_saliente")
    private OrdenSaliente ordenSaliente;





}
