package com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.WareHouse;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.IWarehouseRepository;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseServiceImpl implements IWarehouseService {


    @Autowired
    IWarehouseRepository warehouseRepository;
    @Override
    public Optional<WareHouse> findById(Integer id) {
        return warehouseRepository.findById(id);
    }
}
