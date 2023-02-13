package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;


import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.ProductDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ProductOrderDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product where product.id in (select distinct product_id from batch where section_id in (SELECT id FROM section where category_id IN (select id from category where code = :code)))", nativeQuery = true)
    List<Product> findProductByCategory(@Param("code") String code);

    @Query(value = "SELECT SUM(current_quantity) FROM batch where product_id = :productId", nativeQuery = true)
    Long findCurrentQuantityByProductId(@Param("productId") Integer productId);

    @Query(value = "Select due_date from batch where product_id= :productId", nativeQuery = true)
    LocalDate findDueDateByProduct(@Param("productId") Integer productId);

    @Query(value = "select product.id as productId, product.name as name, product.price as price,purchase_order_has_product.quantity as quantity from product inner join purchase_order_has_product on product.id = purchase_order_has_product.product_id and purchase_order_has_product.purchase_order_id= :orderId", nativeQuery = true)
    List<ProductOrderDTO> findProductByOrderId(@Param("orderId") Integer orderId);

    //Product findById(int id);
}
