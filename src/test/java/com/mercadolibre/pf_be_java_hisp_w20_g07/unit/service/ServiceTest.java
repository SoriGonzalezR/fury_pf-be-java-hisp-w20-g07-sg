package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.SectionDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IWarehouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    IWarehouseService warehouseService;

    @Mock
    IUserRepository userRepository;

    @Mock
    ISectionRepository sectionRepository;

    @Mock
    IInboundOrderRepository iInboundOrderRepository;

    @Mock
    IProductRepository productRepository;

    @Mock
    IBatchRepository batchRepository;

    //@Test
    //@DisplayName("save inbound order ok")
    //public void save(){
    //    //Arrange
    //    InboundOrderRequestDto inboundOrderRequestDto = new InboundOrderRequestDto();
    //    SectionDto sectionDto = new SectionDto(1,1);
    //    List<BatchDto> batchDtos = new ArrayList<>();
    //    batchDtos.add(new BatchDto(10,1,10,12,400,400,LocalDate.parse("10-01-2023"), new Date("01-01-0001 12:30:00"),LocalDate.parse("10-01-2030")));
    //    InboundOrderDto inboundOrderDto = new InboundOrderDto(1, LocalDate.parse("12-10-2023"),
    //            sectionDto, batchDtos);
    //    //Act
    //    //Assert
    //}
}
