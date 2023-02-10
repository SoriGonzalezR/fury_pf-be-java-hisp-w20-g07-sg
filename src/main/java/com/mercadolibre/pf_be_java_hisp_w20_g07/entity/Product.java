package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private double price;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<Batch> batches;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<PurchaseOrderHasProduct> purchaseOrderHasProducts;


}
