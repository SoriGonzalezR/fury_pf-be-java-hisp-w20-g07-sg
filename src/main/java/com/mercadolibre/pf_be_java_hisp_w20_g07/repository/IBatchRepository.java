package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBatchRepository extends JpaRepository<Batch,Integer> {

    List<Batch> findByProductId(Integer productId);
}
