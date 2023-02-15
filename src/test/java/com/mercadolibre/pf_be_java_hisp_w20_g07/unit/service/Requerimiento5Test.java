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
public class Requerimiento5Test {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    IUserRepository userRepository;

    @Test
    @DisplayName("find Batches Due To ExpireSoon")
    public void findBatchesDueToExpireSoonNotOk(){
        //Arrange
        String username = "tomas";
        //Act
        //Se mockean los datos para las validaciones
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        //Assert
        assertThrows(UserNotFoundException.class, () -> productService.findBatchesDueToExpireSoon(1,username));
    }

    @Test
    @DisplayName("find Batches Due To ExpireSoon")
    public void findBatchesDueToExpireSoonCategoryNotOk(){
        //Arrange
        String username = "tomas";
        //Act
        //Se mockean los datos para las validaciones
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        //Assert
        assertThrows(UserNotFoundException.class, () -> productService.findBatchesDueToExpireSoon(1,"date_asc","FS",username));
    }
}