package com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {


    @Override
    public List<Product> getProducts(int productId) {
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return null;
    }

    @Override
    public String createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) {

        return null;
    }

    @Override
    public Double calculateTotalPrice() {
        return null;
    }

    @Override
    public PurchaseOrderRequestDTO getOrder(int orderId) {
        return null;
    }

    @Override
    public PurchaseOrderRequestDTO updateOrder(int orderId) {
        return null;
    }

}
