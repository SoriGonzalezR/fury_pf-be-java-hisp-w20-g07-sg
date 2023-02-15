package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.StockResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.WareHouse;

import java.util.List;
import java.util.Optional;

public interface IWarehouseService {

    List<WareHouse> findAll();

    StockResponseDto getStockbyProduct(Integer productId);
}
