package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class InboundOrderRequestDto {
    @NotNull(message = "inbound_order cannot be null")
    @Valid
    InboundOrderDto inboundOrder;
}
