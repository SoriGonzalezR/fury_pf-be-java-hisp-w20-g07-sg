package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ProductOrderResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ProductResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.PurchaseOrderResponseDTO;

import java.util.List;

public interface IProductService {

    List<ProductResponseDTO> getProducts();

    List<ProductResponseDTO> getProductsByCategory(String code);

    PurchaseOrderResponseDTO createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    Double calculateTotalPrice();

    List<ProductOrderResponseDTO> getOrder(int orderId);

    String updateOrder(int orderId, PurchaseOrderRequestDTO purchaseOrderRequestDTO);

}
