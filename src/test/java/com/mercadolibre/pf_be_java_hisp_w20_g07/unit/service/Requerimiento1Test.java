package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.SectionDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IWarehouseService;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.ProductServiceImpl;
import com.mercadolibre.pf_be_java_hisp_w20_g07.unit.utils.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Requerimiento1Test {

    @InjectMocks
    ProductServiceImpl productService;
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

    Utils utils;



    @Test
    @DisplayName("save inbound order ok")
    public void saveOK(){
        //Arrange
        String username = "Tomas";
        InboundOrderRequestDto inboundOrderRequestDto = new InboundOrderRequestDto();

        SectionDto sectionDto = new SectionDto(1,1);
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(10,1,13,12,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(11,2,11,10,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(1, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        WareHouse mockWarehouse = Utils.wareHouses().get(0);
        User user = Utils.users().get(0);
        Section section = Utils.sections().get(0);
        Product product = Utils.products().get(0);
        InboundOrder inboundOrder1 = Utils.inboundOrders().get(0);
        InboundOrder inboundOrder2 = Utils.inboundOrders().get(1);
        //Act
        when(warehouseService.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        when(productRepository.findById(2)).thenReturn(Optional.ofNullable(product));
        //when(iInboundOrderRepository.save(inboundOrder1)).thenReturn(inboundOrder1);
        //doReturn(inboundOrder2).when(iInboundOrderRepository.save(inboundOrder2));



        InboundOrderResponseDto actual = productService.save(inboundOrderRequestDto,username);

        //Assert

        Assert.assertEquals(inboundOrderDto.getBatchStock(),actual.getBatchStock());
    }
}
