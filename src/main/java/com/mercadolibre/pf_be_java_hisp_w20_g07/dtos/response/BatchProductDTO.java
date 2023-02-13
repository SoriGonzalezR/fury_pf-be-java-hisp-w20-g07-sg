package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchProductDTO {

    private Integer batch_number;
    private Integer current_quantity;
    private LocalDate due_date;
}
