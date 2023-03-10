package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inbound_order")
public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "inboundOrder")
    private List<Batch> batchStock;


}
