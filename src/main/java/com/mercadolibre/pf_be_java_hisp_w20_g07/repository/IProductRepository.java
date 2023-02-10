package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;

public interface IProductRepository {

    Product findById(int id);

    boolean save(Product product);

}
