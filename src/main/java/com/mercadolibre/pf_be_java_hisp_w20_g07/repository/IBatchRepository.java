package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IBatchRepository extends JpaRepository<Batch,Integer> {


    @Query(value = "select * from batch where product_id = :idProduct", nativeQuery = true)
    List<Batch> findBatchByProduct(@Param("idProduct") Integer idProduct);

    @Query(value = "select b.section_id from product p join batch b on p.id = b.product_id join section s on s.id = b.section_id join warehouse w on w.id = s.warehouse_id  where w.id = :idWarehouse and b.product_id = :idProduct", nativeQuery = true)
    Integer findSectionByWarehouseAndProduct(@Param("idWarehouse") Integer idWarehouse, @Param("idProduct")Integer idProduct);

    @Query("SELECT b FROM Batch b WHERE b.section.warehouse.id = :warehouseId AND b.section.category.code = :categoryId AND b.dueDate BETWEEN :startDate AND :endDate ORDER BY b.dueDate asc")
    List<Batch> findBatchesAsc(int warehouseId, LocalDate startDate, LocalDate endDate, String categoryId);

    @Query("SELECT b FROM Batch b WHERE b.section.warehouse.id = :warehouseId AND b.section.category.code = :categoryId AND b.dueDate BETWEEN :startDate AND :endDate ORDER BY b.dueDate desc")
    List<Batch> findBatchesDesc(int warehouseId, LocalDate startDate, LocalDate endDate, String categoryId);


    @Query("SELECT b FROM Batch b WHERE b.section.warehouse.id = :warehouseId AND b.dueDate BETWEEN :startDate AND :endDate")
    List<Batch> findBatches(int warehouseId, LocalDate startDate, LocalDate endDate);

    List<Batch> findByProductId(Integer productId);
}

