package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBatchRepository extends JpaRepository<Batch,Integer> {
}
