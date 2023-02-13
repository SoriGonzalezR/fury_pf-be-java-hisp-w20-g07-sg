package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ProductOrderResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ProductResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.PurchaseOrderResponseDTO;

import java.util.List;

public interface IProductService {

    InboundOrderResponseDto save(InboundOrderRequestDto inboundOrderRequestDto, String username);

    InboundOrderResponseDto update(InboundOrderRequestDto inboundOrderRequestDto, String username);

    List<ProductResponseDTO> getProducts();

    List<ProductResponseDTO> getProductsByCategory(String code);

    PurchaseOrderResponseDTO createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    Double calculateTotalPrice();

    List<ProductOrderResponseDTO> getOrder(int orderId);

    String updateOrder(int orderId, PurchaseOrderRequestDTO purchaseOrderRequestDTO);

}

