package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @ManyToOne()
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "purchaseOrder")
    private List<PurchaseOrderHasProduct> purchaseOrderHasProducts;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
