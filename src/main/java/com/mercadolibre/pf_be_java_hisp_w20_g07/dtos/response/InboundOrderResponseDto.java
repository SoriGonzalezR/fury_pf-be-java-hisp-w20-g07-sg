package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InboundOrderResponseDto {
    private List<BatchDto> batchStock;
}
