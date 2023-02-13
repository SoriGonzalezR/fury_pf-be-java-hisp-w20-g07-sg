package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "minimum_temperature")
    private double minimumTemperature;
    @Column(name = "maximum_temperature")
    private double maximumTemperature;
    @Column(name = "maximum_batch_quantity")
    private int maximumBatchQuantity;

    @ManyToOne()
    @JoinColumn(name = "warehouse_id")
    private WareHouse warehouse;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "section",fetch = FetchType.EAGER)
    private List<Batch> batches;

}
