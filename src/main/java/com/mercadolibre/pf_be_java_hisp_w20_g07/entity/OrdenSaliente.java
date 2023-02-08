package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orden_saliente")
public class OrdenSaliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fecha;

    //mappear
    private EstadoOrden estadoOrden;

    //mappear
    private List<OrdenSalienteHasProducto> ordenSalienteHasProductos;
}
