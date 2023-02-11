package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InboundOrderDto {

    private int OrderNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate orderDate;

    private SectionDto section;

    private List<BatchDto> batchStock;
}





