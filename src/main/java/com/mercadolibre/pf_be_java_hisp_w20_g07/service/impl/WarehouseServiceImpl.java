package com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.WarehouseStockDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.StockResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Batch;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.WareHouse;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.ResourceNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.IProductRepository;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.IWarehouseRepository;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements IWarehouseService {

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IWarehouseRepository warehouseRepository;

    @Override
    public Optional<WareHouse> findById(Integer id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public List<WareHouse> findAll() {
        return warehouseRepository.findAll();
    }

    @Override
    @Transactional

    //Requisito 4
    public StockResponseDto getStockbyProduct(Integer productId) {
        productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId +" not found"));

        StockResponseDto stock = new StockResponseDto();
        stock.setProductId(productId);

        List<WareHouse> whs = findAll();

        List<WarehouseStockDto> whStock = whs.stream()
                .map(wh -> {
                    int quantity = wh.getSections().stream()
                            .mapToInt(section -> section.getBatches().stream()
                                    .filter(batch -> batch.getProduct().getId() == productId)
                                    .mapToInt(Batch::getCurrentQuantity)
                                    .sum())
                            .sum();

                    return new WarehouseStockDto(wh.getId(), quantity);
                })
                .collect(Collectors.toList());

        stock.setWarehouses(whStock);

        return stock;
    }
}