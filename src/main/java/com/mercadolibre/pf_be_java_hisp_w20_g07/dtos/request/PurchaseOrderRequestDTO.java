package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.OrderStatusDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

//Cambiar nombre por PurchaseOrderRequestDTO
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PurchaseOrderRequestDTO {
    @NotNull(message = "La fecha no puede estar vaciá")

    private LocalDate date;
    private Integer buyerId;
    private OrderStatusDTO orderStatus;
    private List<ProductDTO> product;

}
