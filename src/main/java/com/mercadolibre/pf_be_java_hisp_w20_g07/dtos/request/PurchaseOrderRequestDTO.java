package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.OrderStatusDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.ProductDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

//Cambiar nombre por PurchaseOrderRequestDTO
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseOrderRequestDTO {
    @NotNull(message = "date mustn't be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "buyer id is required")
    private Integer buyerId;

    @NotNull(message = "status is required")
    private OrderStatusDTO orderStatus;
    @NotNull(message = "products are required")
    private List<ProductDTO> product;

}
