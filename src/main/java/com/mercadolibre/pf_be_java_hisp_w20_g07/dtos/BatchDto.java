package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.InboundOrder;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BatchDto {

    private Integer id;

    private Product product;

    private double currentTemperature;

    private double minimumTemperature;
    private int initialQuantity;
    private int currentQuantity;
    private LocalDate manufacturingDate;
    private LocalDate manufacturingTime;
    private LocalDate dueDate;


    //private Section section;
    //private InboundOrder inboundOrder;




}
