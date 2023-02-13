package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.SectionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDTO {
    private SectionDto section;
    private Integer idProduct;
    private List<BatchProductDTO> batchStockProduct;
}
