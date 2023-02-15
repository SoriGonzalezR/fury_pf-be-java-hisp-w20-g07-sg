package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer batchNumber;

    @Column(name = "initial_quantity")
    private int initialQuantity;
    @Column(name = "current_quantity")
    private int currentQuantity;

    @Column(name = "minimum_temperature")
    private Double minimumTemperature;

    @Column(name = "current_temperature")
    private Double currentTemperature;
    @ManyToOne()
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "inbound_order_id")
    private InboundOrder inboundOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "manufacturing_date")
    private LocalDate manufacturingDate;
    @Column(name = "manufacturing_time")
    private LocalDateTime manufacturingTime;
    @Column(name = "due_date")
    private LocalDate dueDate;



}

