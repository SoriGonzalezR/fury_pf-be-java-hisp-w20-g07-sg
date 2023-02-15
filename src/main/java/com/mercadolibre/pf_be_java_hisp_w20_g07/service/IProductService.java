package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.BatchStockDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import java.util.List;

public interface IProductService {

    InboundOrderResponseDto save(InboundOrderRequestDto inboundOrderRequestDto, String username);

    InboundOrderResponseDto update(InboundOrderRequestDto inboundOrderRequestDto, String username);

    BatchStockDTO productInStock(Integer idProduct, String order, String username);

    List<ProductResponseDTO> getProducts();

    List<ProductResponseDTO> getProductsByCategory(String code);

    PurchaseOrderResponseDTO createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    List<ProductOrderResponseDTO> getOrder(int orderId);

    String updateOrder(int orderId, PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    FindBatchesDueToExpireSoonDto findBatchesDueToExpireSoon(int days, String userName);
    
    FindBatchesDueToExpireSoonDto findBatchesDueToExpireSoon(int days, String order, String category, String userName);

}

