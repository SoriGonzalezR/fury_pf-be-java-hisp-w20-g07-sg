package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InboundOrderResponseDto {
    private List<BatchDto> batchStock;
}
