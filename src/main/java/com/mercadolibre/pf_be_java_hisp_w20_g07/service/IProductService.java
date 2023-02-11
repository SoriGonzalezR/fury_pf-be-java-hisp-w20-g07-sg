package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;

public interface IProductService {

    InboundOrderResponseDto save(InboundOrderRequestDto inboundOrderRequestDto);

    InboundOrderResponseDto update(InboundOrderRequestDto inboundOrderRequestDto);
}
