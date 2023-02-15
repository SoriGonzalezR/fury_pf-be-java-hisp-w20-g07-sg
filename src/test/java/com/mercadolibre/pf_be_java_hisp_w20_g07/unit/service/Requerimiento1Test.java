package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.SectionDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.ResourceNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.UserNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.ProductServiceImpl;
import com.mercadolibre.pf_be_java_hisp_w20_g07.unit.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
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
        when(iInboundOrderRepository.save(Mockito.any(InboundOrder.class))).thenAnswer(i -> i.getArguments()[0]);

        //Respuesta Actual
        InboundOrderResponseDto actual =  productService.save(inboundOrderRequestDto,username);

        //Assert
        System.out.println(expected);
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("update inbound order ok")
    public void updateOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(2,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        WareHouse mockWarehouse = Utils.wareHouses().get(0);
        User user = Utils.users().get(0);
        Section section = Utils.sections().get(0);
        Product product = Utils.products().get(0);
        InboundOrder inboundOrder1 = Utils.inboundOrders().get(0);
        InboundOrder inboundOrder2 = Utils.inboundOrders().get(1);
        InboundOrderResponseDto expected = new InboundOrderResponseDto(batchDtos);

        //Act
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        when(productRepository.findById(2)).thenReturn(Optional.ofNullable(product));

        //Assert

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        when(productRepository.findById(2)).thenReturn(Optional.ofNullable(product2));
        when(batchRepository.existsById(1)).thenReturn(true);
        when(batchRepository.existsById(2)).thenReturn(true);

        //Respuesta Actual
        InboundOrderResponseDto actual =  productService.update(inboundOrderRequestDto,username);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("save inbound order warehouse not found")
    public void saveWarehouseNotFoundNotOk(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.save(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("save inbound order user not found")
    public void saveUserNotFoundNotOk(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        //Assert
        assertThrows(UserNotFoundException.class, () -> productService.save(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("save inbound order user dont belong to warehouse")
    public void saveUserNotBelongToWarehouseNotOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        User user = Utils.users().get(1);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.save(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("save inbound order section not valid")
    public void saveSectionNotValidNotOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(null));

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.save(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("save inbound order batch doesn't belong to the section")
    public void saveBatchDoesNotBelongToWarehouseNotOK(){
        //Arrange

        SectionDto sectionDto = new SectionDto(1,2);
        User user = Utils.users().get(1);

        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,50.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(2)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.save(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("save inbound order Not Enough Space in section")
    public void saveNotEnoughSpaceNotOK(){
        //Arrange

        Section section = new Section(1,10,16,1,Utils.wareHouses().get(0),
                new Category(1,"FF","Fresh Food",null),Utils.batchStocks().get(0));

        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,11.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.save(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("save inbound order Batch already exist")
    public void saveBatchAlreadyExistNotOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,11.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        when(batchRepository.existsById(1)).thenReturn(true);
        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.save(inboundOrderRequestDto,username));
    }

    //UPDATE NOT OK
    @Test
    @DisplayName("update inbound order warehouse not found")
    public void updateWarehouseNotFoundNotOk(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.update(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("update inbound order user not found")
    public void updateUserNotFoundNotOk(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        //Assert
        assertThrows(UserNotFoundException.class, () -> productService.update(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("update inbound order user dont belong to warehouse")
    public void updateUserNotBelongToWarehouseNotOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        User user = Utils.users().get(1);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.update(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("update inbound order section not valid")
    public void updateSectionNotValidNotOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,10.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(null));

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.update(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("update inbound order batch doesn't belong to the section")
    public void updateBatchDoesNotBelongToWarehouseNotOK(){
        //Arrange

        SectionDto sectionDto = new SectionDto(1,2);
        User user = Utils.users().get(1);

        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,50.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(2)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.update(inboundOrderRequestDto,username));
    }

    @Test
    @DisplayName("update inbound order Batch already exist")
    public void updateBatchNotExistNotOK(){
        //Arrange
        List<BatchDto> batchDtos = new ArrayList<>();
        batchDtos.add(new BatchDto(1,1,13.0,12.0,400,400,
                LocalDate.parse("2022-11-01"),
                LocalDateTime.parse("0001-01-01T12:30:00"),
                LocalDate.parse("2025-10-01")));
        batchDtos.add(new BatchDto(1,2,11.0,11.0,200,200,
                LocalDate.parse("2022-12-01"),
                LocalDateTime.parse("0001-01-01T13:30:00"),
                LocalDate.parse("2024-10-01")));
        InboundOrderDto inboundOrderDto = new InboundOrderDto(15, LocalDate.parse("2023-12-10"),
                sectionDto, batchDtos);
        inboundOrderRequestDto.setInboundOrder(inboundOrderDto);

        //Act
        //Se mockean los datos para las validaciones
        when(warehouseRepository.findById(1)).thenReturn(Optional.ofNullable(mockWarehouse));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(sectionRepository.findSectionByIdAndWarehouse(1,mockWarehouse)).thenReturn(Optional.ofNullable(section));
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        when(batchRepository.existsById(1)).thenReturn(false);
        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.update(inboundOrderRequestDto,username));
    }
}
