package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.service;


import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.SectionDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.BatchProductDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.BatchStockDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.BatchNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.ResourceNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.UserNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Requerimiento3Test {


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


    @Test
    @DisplayName("product in stock")
    public void productInStockOK() {
        //Arrange
        SectionDto sectionDto = new SectionDto();
        BatchProductDTO batchProductDTO = new BatchProductDTO();
        List<BatchProductDTO> batchProductDTOList = new ArrayList<>();
        sectionDto.setSectionCode(1);
        sectionDto.setWarehouseCode(1);
        batchProductDTO.setBatch_number(1);
        batchProductDTO.setCurrent_quantity(50);
        batchProductDTO.setDue_date(LocalDate.parse("2023-05-20"));
        batchProductDTOList.add(batchProductDTO);

        //Respuesta Esperada
        BatchStockDTO anwserExpect = new BatchStockDTO(sectionDto, 1, batchProductDTOList);

        Product product = new Product(1, "Fresa", 2000.00, null, null);
        WareHouse wareHouseMock = new WareHouse(1, "warehouse1", "Colombia", "Bogota", "cll 185 #10-3", null, null);
        User user = new User(1, "Tomas", "123tomas", new Role(1, "1", null), wareHouseMock, null);
        List<Batch> batchList = new ArrayList<>();
        Section sectionMock = new Section(1,10,16,10,wareHouseMock,new  Category(1,"FF","Fresh Food",null), batchList);
        batchList.add(new Batch(1, 200, 50, 12.0, 12.0, sectionMock, null, product, null, null, LocalDate.parse("2023-05-20")));

        //Act
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(batchRepository.findBatchByProduct(1)).thenReturn(batchList);
        when(batchRepository.findSectionByWarehouseAndProduct(1, 1)).thenReturn(1);

        //Respuesta Actual
        BatchStockDTO actual =  productService.productInStock(1, null,"Tomas");

        //Assert
        Assertions.assertEquals(anwserExpect, actual);

    }


    @Test
    @DisplayName("product in stock order")
    public void productInStockOrderOK() {
        //Arrange
        SectionDto sectionDto = new SectionDto();
        BatchProductDTO batchProductDTO = new BatchProductDTO();
        List<BatchProductDTO> batchProductDTOList = new ArrayList<>();
        sectionDto.setSectionCode(1);
        sectionDto.setWarehouseCode(1);
        batchProductDTO.setBatch_number(1);
        batchProductDTO.setCurrent_quantity(50);
        batchProductDTO.setDue_date(LocalDate.parse("2023-05-20"));
        batchProductDTOList.add(batchProductDTO);

        //Respuesta Esperada
        BatchStockDTO anwserExpect = new BatchStockDTO(sectionDto, 1, batchProductDTOList);

        Product product = new Product(1, "Fresa", 2000.00, null, null);
        WareHouse wareHouseMock = new WareHouse(1, "warehouse1", "Colombia", "Bogota", "cll 185 #10-3", null, null);
        User user = new User(1, "Tomas", "123tomas", new Role(1, "1", null), wareHouseMock, null);
        List<Batch> batchList = new ArrayList<>();
        Section sectionMock = new Section(1,10,16,10,wareHouseMock,new  Category(1,"FF","Fresh Food",null), batchList);
        batchList.add(new Batch(1, 200, 50, 12.0, 12.0, sectionMock, null, product, null, null, LocalDate.parse("2023-05-20")));

        //Act
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(batchRepository.findBatchByProduct(1)).thenReturn(batchList);
        when(batchRepository.findSectionByWarehouseAndProduct(1, 1)).thenReturn(1);

        //Respuesta Actual
        BatchStockDTO actualOrderL =  productService.productInStock(1, "L","Tomas");
        BatchStockDTO actualOrderC =  productService.productInStock(1, "C","Tomas");
        BatchStockDTO actualOrderF =  productService.productInStock(1, "F","Tomas");

        //Assert
        Assertions.assertEquals(anwserExpect, actualOrderL);
        Assertions.assertEquals(anwserExpect, actualOrderC);
        Assertions.assertEquals(anwserExpect, actualOrderF);
        assertThrows(BatchNotFoundException.class, () -> productService.productInStock(1, "M","Tomas"));

    }

    @Test
    @DisplayName("Product in stock - Product not found")
    public void productInStockNotuserFound(){
        //Arrange
        String username = "tomas";
        //Act
        //Se mockean los datos para las validaciones
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.productInStock(1, "L", username));
    }






}












