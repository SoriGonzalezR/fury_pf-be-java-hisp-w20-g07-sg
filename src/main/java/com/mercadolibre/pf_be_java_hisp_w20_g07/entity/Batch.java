package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lote")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "initial_quantity")
    private int initialQuantity;
    @Column(name = "current_quantity")
    private int currentQuantity;

    @Column(name = "minimum_temperature")
    private double minimumTemperature;
    @ManyToOne()
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne()
    @JoinColumn(name = "inbound_order_id")
    private InboundOrder inboundOrder;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;


}
