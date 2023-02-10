package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderResponseDto;

public interface IInboundOrderService {

    InboundOrderResponseDto save(InboundOrderDto inboundOrderDto);

    InboundOrderResponseDto update(InboundOrderDto inboundOrderDto);


}
