package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponseDTO {
    private Integer id;
    private String name;
    private double price;
}
