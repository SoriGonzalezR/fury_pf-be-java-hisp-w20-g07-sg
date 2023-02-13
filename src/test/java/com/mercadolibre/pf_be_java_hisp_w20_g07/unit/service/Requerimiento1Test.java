package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.SectionDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.ProductServiceImpl;
import com.mercadolibre.pf_be_java_hisp_w20_g07.unit.utils.Utils;
import org.junit.jupiter.api.*;
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
    IWarehouseRepository warehouseRepository;
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

    //Variables de clase
    static WareHouse mockWarehouse;
    static User user;
    static Section section;
    static Product product1;
    static Product product2;
    static InboundOrderRequestDto inboundOrderRequestDto;
    static String username;
    static SectionDto sectionDto;

    @BeforeAll
    static void setup(){
        mockWarehouse = Utils.wareHouses().get(0);
        user = Utils.users().get(0);
        section = Utils.sections().get(0);
        product1 = Utils.products().get(0);
        product2 = Utils.products().get(1);
        inboundOrderRequestDto = new InboundOrderRequestDto();
        username = "Tomas";
        sectionDto = new SectionDto(1,1);
    }

    @Test
    @DisplayName("save inbound order ok")
    public void saveOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(null,1,13,12,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(null,2,11,10,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Respuesta Esperada
        InboundOrderResponseDto expected = new InboundOrderResponseDto(batchDtos);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        when(productRepository.findById(2)).thenReturn(Optional.ofNullable(product2));

        //Respuesta Actual
        InboundOrderResponseDto actual =  productService.save(inboundOrderRequestDto,username);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("update inbound order ok")
    public void updateOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13,12,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(2,2,11,10,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Respuesta Esperada
        InboundOrderResponseDto expected = new InboundOrderResponseDto(batchDtos);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        when(productRepository.findById(2)).thenReturn(Optional.ofNullable(product2));
        when(batchRepository.existsById(1)).thenReturn(true);
        when(batchRepository.existsById(2)).thenReturn(true);

        //Respuesta Actual
        InboundOrderResponseDto actual =  productService.update(inboundOrderRequestDto,username);

        //Assert
        Assertions.assertEquals(expected, actual);
    }
}
