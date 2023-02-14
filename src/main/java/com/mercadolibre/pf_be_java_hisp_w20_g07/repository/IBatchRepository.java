package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBatchRepository extends JpaRepository<Batch,Integer> {

    @Query(value = "select * from batch where product_id = :idProduct", nativeQuery = true)
    List<Batch> findBatchByProduct(@Param("idProduct") Integer idProduct);
}
