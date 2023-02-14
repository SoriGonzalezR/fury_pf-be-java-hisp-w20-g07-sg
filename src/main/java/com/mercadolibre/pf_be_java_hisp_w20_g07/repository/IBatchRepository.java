package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBatchRepository extends JpaRepository<Batch,Integer> {

    @Query(value = "select * from batch where product_id = :idProduct", nativeQuery = true)
    List<Batch> findBatchByProduct(@Param("idProduct") Integer idProduct);

    @Query(value = "select b.section_id from product p join batch b on p.id = b.product_id join section s on s.id = b.section_id join warehouse w on w.id = s.warehouse_id  where w.id = 1 and b.product_id = 1", nativeQuery = true)
    Integer getSectionId();
}
