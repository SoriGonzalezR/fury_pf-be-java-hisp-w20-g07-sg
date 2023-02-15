package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusDTO {

    @NotBlank(message = "status_code cannot be blank")
    private String statusCode;

}
