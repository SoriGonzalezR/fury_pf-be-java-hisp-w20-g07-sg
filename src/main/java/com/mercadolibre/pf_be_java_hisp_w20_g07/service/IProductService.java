package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> getProducts(int productId);

    List<Product> getProductsByCategory(String category);

    String createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    Double calculateTotalPrice();

    PurchaseOrderRequestDTO getOrder(int orderId);

    PurchaseOrderRequestDTO updateOrder(int orderId);

}
