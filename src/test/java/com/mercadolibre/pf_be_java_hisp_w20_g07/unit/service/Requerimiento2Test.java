package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.OrderStatusDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.ProductDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ProductResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.User;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.NotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.IProductRepository;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.IUserRepository;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Requerimiento2Test {
    @Mock
    private IProductRepository productRepository;

    @Mock
    private IUserRepository usersRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findByIdTest() {
        List<ProductResponseDTO> productsExpect = Arrays.asList(new ProductResponseDTO(1,"Fresa",2000.00),
                new ProductResponseDTO(2,"Pollo",12000.00));
        List<Product> products = Arrays.asList(new Product(1,"Fresa",2000.0, null,null),
                new Product(2,"Pollo",12000.0, null,null));

        when(productRepository.findAll()).thenReturn(products);
        List<ProductResponseDTO> productsActual = productService.getProducts();

        assertEquals(productsExpect.get(0).getId(), productsActual.get(0).getId());
        assertEquals(productsExpect.get(0).getName(), productsActual.get(0).getName());
        assertEquals(productsExpect.get(0).getPrice(), productsActual.get(0).getPrice());
        assertEquals(productsExpect.get(1).getName(), productsActual.get(1).getName());
        assertEquals(productsExpect.get(1).getPrice(), productsActual.get(1).getPrice());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void notFoundUserException (){
        OrderStatusDTO orderStatus = new OrderStatusDTO("carrito");
        List<ProductDTO> productDTOList = Arrays.asList(new ProductDTO(1, 2),
                new ProductDTO(2,2));
        PurchaseOrderRequestDTO purchaseOrderRequestDTO = new PurchaseOrderRequestDTO(LocalDate.now(),4,orderStatus,productDTOList);

        when(usersRepository.findById(4)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> {
            productService.createPurchaseOrder(purchaseOrderRequestDTO);
        });
    }
    @Test
    void notFoundProductsException (){
        OrderStatusDTO orderStatus = new OrderStatusDTO("carrito");
        List<ProductDTO> productDTOList = Arrays.asList(new ProductDTO(1, 2));
        LocalDate date = LocalDate.parse("2023-01-24" );
        PurchaseOrderRequestDTO purchaseOrderRequestDTO = new PurchaseOrderRequestDTO(LocalDate.now(),1,orderStatus,productDTOList);
        when(usersRepository.findById(1)).thenReturn(Optional.of(new User(1,"Tomas","tomas123",null,null,null)));
        when(productRepository.findCurrentQuantityByProductId(1)).thenReturn(1L);
        assertThrows(NotFoundException.class, () -> {
            productService.createPurchaseOrder(purchaseOrderRequestDTO);
        });
    }
    @Test
    void dateDueSoonException (){
        OrderStatusDTO orderStatus = new OrderStatusDTO("carrito");
        List<ProductDTO> productDTOList = List.of(new ProductDTO(1, 3));
        LocalDate date = LocalDate.parse("2023-02-10" );
        PurchaseOrderRequestDTO purchaseOrderRequestDTO = new PurchaseOrderRequestDTO(LocalDate.now(),1,orderStatus,productDTOList);
        when(usersRepository.findById(1)).thenReturn(Optional.of(new User(1,"Tomas","tomas123",null,null,null)));
        when(productRepository.findCurrentQuantityByProductId(1)).thenReturn(10L);
        //when(productRepository.findDueDateByProduct(1)).thenReturn(LocalDate.parse("2023-03-16"));
        assertThrows(RuntimeException.class, () -> {
            productService.createPurchaseOrder(purchaseOrderRequestDTO);
        });
    }
}
