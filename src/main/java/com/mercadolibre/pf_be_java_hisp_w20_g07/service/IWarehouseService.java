package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.WareHouse;

import java.util.Optional;

public interface IWarehouseService {

    Optional<WareHouse> findById(Integer id);
}
