package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.OrderStatus;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

//Cambiar nombre por PurchaseOrderRequestDTO
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PurchaseOrderRequestDTO {

    private LocalDate date;
    private Integer buyer_id;
    private OrderStatus order_status;
    private List<Product> products;

}
