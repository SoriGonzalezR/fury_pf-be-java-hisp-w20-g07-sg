package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.PurchaseOrderHasProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IPurchaseOrderHasProductRepository extends JpaRepository<PurchaseOrderHasProduct, Integer> {

    @Query(value = "SELECT purchase_order_has_product.* FROM purchase_order_has_product WHERE purchase_order_has_product.purchase_order_id = :orderId", nativeQuery = true)
    List<PurchaseOrderHasProduct> findByPurchaseOrder(Integer orderId);

    @Query(value = "select purchase_order_has_product.product_id from purchase_order_has_product where purchase_order_has_product.purchase_order_id = :orderId", nativeQuery = true)
    List<Integer> findProductIdByOrderId(@Param("orderId") Integer orderId);

    @Modifying
    @Query(value = "delete from purchase_order_has_product where purchase_order_has_product.product_id= :productId and purchase_order_has_product.purchase_order_id= :orderId", nativeQuery = true)
    void deleteByProductIdAndPurchaseOrderId(@Param("productId") Integer productId, @Param("orderId") int orderId);
}
