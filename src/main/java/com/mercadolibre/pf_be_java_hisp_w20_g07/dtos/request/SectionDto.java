package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SectionDto {
    @Min(value = 1,message = "section_code must be grater than 0")
    @NotNull(message = "section_code cannot be null")
    private Integer sectionCode;
    @Min(value = 1,message = "warehouse_code must be grater than 0")
    @NotNull(message = "warehouse_code cannot be null")
    private Integer warehouseCode;
}

