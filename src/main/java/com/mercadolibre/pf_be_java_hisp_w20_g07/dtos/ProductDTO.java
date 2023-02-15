package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @NotNull(message = "product_id cannot be null")
    @Min(value = 1, message = "product_id cannot be lower than 1")
    private Integer productId;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 0, message = "quantity cannot be lower than 0")
    private Integer quantity;

}
