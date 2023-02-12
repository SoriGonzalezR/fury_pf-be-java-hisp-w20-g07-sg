package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Section;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ISectionRepository extends JpaRepository<Section,Integer> {

    @Transactional
    Optional<Section> findSectionByIdAndWarehouse(Integer id, WareHouse wareHouse);


}
