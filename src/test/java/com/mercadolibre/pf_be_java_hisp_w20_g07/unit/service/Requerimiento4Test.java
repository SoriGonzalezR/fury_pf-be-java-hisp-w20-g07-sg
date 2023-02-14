package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.StockResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IWarehouseService;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.WarehouseServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Requerimiento4Test{

    @Mock
    IProductRepository productRepository;

    @InjectMocks
    WarehouseServiceImpl warehouseService;

    @Mock
    IWarehouseRepository warehouseRepository;

    @Mock
    IWarehouseService iWarehouseService;

    @Test
    public void testGetStockbyProduct() {
        int productId = 1;
        Product product = new Product();
        product.setId(1);

        Product product2 = new Product();
        product2.setId(2);

        WareHouse warehouse1 = new WareHouse();
        warehouse1.setId(1);

        WareHouse warehouse2 = new WareHouse();
        warehouse2.setId(2);

        List<WareHouse> whs = new ArrayList<>();
        whs.add(warehouse1);
        whs.add(warehouse2);

        Batch batch1 = new Batch();
        batch1.setBatchNumber(1);
        batch1.setProduct(product);
        batch1.setCurrentQuantity(15);

        Batch batch2 = new Batch();
        batch2.setBatchNumber(2);
        batch2.setProduct(product);
        batch2.setCurrentQuantity(15);

        Batch batch3 = new Batch();
        batch3.setBatchNumber(3);
        batch3.setProduct(product2);

        Batch batch4 = new Batch();
        batch4.setBatchNumber(4);
        batch4.setProduct(product);
        batch4.setCurrentQuantity(10);

        List<Batch> batches1 = new ArrayList<>();
        batches1.add(batch1);
        batches1.add(batch2);
        List<Batch> batches2 = new ArrayList<>();
        batches2.add(batch3);
        List<Batch> batches3 = new ArrayList<>();
        batches3.add(batch4);

        Section section1 = new Section();
        section1.setBatches(batches1);
        section1.setWarehouse(warehouse1);
        Section section2 = new Section();
        section2.setBatches(batches2);
        Section section3 = new Section();
        section3.setBatches(batches3);

        warehouse1.setSections(List.of(section1, section2));
        warehouse2.setSections(List.of(section3));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(warehouseRepository.findAll()).thenReturn(List.of(warehouse1, warehouse2));

        StockResponseDto result = warehouseService.getStockbyProduct(productId);

        assertEquals(productId, result.getProductId());
        assertEquals(2, result.getWarehouses().size());
        assertEquals(30, result.getWarehouses().get(0).getTotalQuantity());
        assertEquals(10, result.getWarehouses().get(1).getTotalQuantity());
    }




}




