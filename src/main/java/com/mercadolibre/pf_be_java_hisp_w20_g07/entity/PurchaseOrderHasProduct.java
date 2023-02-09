package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "purchase_order_has_product")
public class PurchaseOrderHasProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    //mappear
    //@Column(name = "orden_saliente")
    @ManyToOne()
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;





}
