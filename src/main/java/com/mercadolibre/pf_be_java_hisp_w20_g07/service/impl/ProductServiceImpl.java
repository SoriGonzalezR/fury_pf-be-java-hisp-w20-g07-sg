package com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.ResourceNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.UserNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    IWarehouseRepository warehouseRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ISectionRepository sectionRepository;

    @Autowired
    IInboundOrderRepository iInboundOrderRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IBatchRepository batchRepository;

    ModelMapper modelMapper;

    public ProductServiceImpl() {

        this.modelMapper = new ModelMapper();

    }

    private Batch mapBatchDtoToBatch(BatchDto batchDto,Batch batch, Section section, InboundOrder inboundOrder){

        batch.setSection(section);
        batch.setInboundOrder(inboundOrder);

        //validar existenia del producto
        productRepository.findById(batchDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product wirth id " + batchDto.getProductId() +" not found"));
        batch.setProduct(productRepository.findById(batchDto.getProductId()).get());

        return batch;
    }


    @Override
    public InboundOrderResponseDto save(InboundOrderRequestDto inboundOrderRequestDto,String username) {

        //Validacion de que el warehouse sea valido
        WareHouse wareHouse = warehouseRepository.findById(inboundOrderRequestDto.getInboundOrder().getSection().getWarehouseCode())
                .orElseThrow(() -> new ResourceNotFoundException("warehouse not found"));

        //validacion representante-warehouse valido
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException());

        if(!user.getWareHouse().getId().equals(inboundOrderRequestDto.getInboundOrder().getSection().getWarehouseCode())){
            throw new ResourceNotFoundException("the user does not belong to the warehouse");
        }

        //validacion de quela seccion sea valida
        Section section = sectionRepository.findSectionByIdAndWarehouse(inboundOrderRequestDto.getInboundOrder().getSection().getSectionCode(),wareHouse)
                .orElseThrow(() -> new ResourceNotFoundException("the section does not belong to the warehouse"));

        //validacion de que los lotes correspondan a su respectivo warehouse
        inboundOrderRequestDto.getInboundOrder().getBatchStock().forEach(b -> {
           if(b.getMinimumTemperature() < section.getMinimumTemperature() || b.getMinimumTemperature() > section.getMaximumTemperature()){
               throw new ResourceNotFoundException("batch with id " + b.getBatchNumber() + " doesn't belong to the section");
           }
        });

        //validar que la seccion tenga espacio
        if((section.getBatches().size() + inboundOrderRequestDto.getInboundOrder().getBatchStock().size() > section.getMaximumBatchQuantity())){
            throw new ResourceNotFoundException("there isn't space for all new batches");
        }


        InboundOrder inboundOrder = modelMapper.map(inboundOrderRequestDto.getInboundOrder(),InboundOrder.class);

        //mapeo de batchesDTO a batches
        for(int i = 0; i < inboundOrderRequestDto.getInboundOrder().getBatchStock().size();i++){

            BatchDto batchDto = inboundOrderRequestDto.getInboundOrder().getBatchStock().get(i);
            Batch batch = inboundOrder.getBatchStock().get(i);

            mapBatchDtoToBatch(batchDto,batch,section,inboundOrder);


            //validar que no existan los lostes entranetes
            if(batchRepository.existsById(batchDto.getBatchNumber())){
                throw new ResourceNotFoundException("Batch with id " + batchDto.getBatchNumber() + " already exist");
            }
        }


         inboundOrder = iInboundOrderRepository.save(inboundOrder);

        InboundOrderResponseDto inboundOrderResponseDto = new InboundOrderResponseDto();

        //mapeo de batches batchesDto
        inboundOrderResponseDto.setBatchStock(
                inboundOrder.getBatchStock().stream().map(e -> {
                    int productId = e.getProduct().getId();
                    BatchDto dto = modelMapper.map(e, BatchDto.class);
                    dto.setProductId(productId);
                    return dto;
                }).collect(Collectors.toList()));

        return inboundOrderResponseDto;
    }

    @Override
    public InboundOrderResponseDto update(InboundOrderRequestDto inboundOrderRequestDto, String username) {
        //Validacion de que el warehouse sea valido
        WareHouse wareHouse = warehouseRepository.findById(inboundOrderRequestDto.getInboundOrder().getSection().getWarehouseCode())
                .orElseThrow(() -> new ResourceNotFoundException("warehouse not found"));

        //validacion representante-warehouse valido
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException());

        if(!user.getWareHouse().getId().equals(inboundOrderRequestDto.getInboundOrder().getSection().getWarehouseCode())){
            throw new ResourceNotFoundException("the user does not belong to the warehouse");
        }

        //validacion de quela seccion sea valida
        Section section = sectionRepository.findSectionByIdAndWarehouse(inboundOrderRequestDto.getInboundOrder().getSection().getSectionCode(),wareHouse)
                .orElseThrow(() -> new ResourceNotFoundException("the section does not belong to the warehouse"));

        //validacion de que los lotes correspondan a su respectivo warehouse
        inboundOrderRequestDto.getInboundOrder().getBatchStock().forEach(b -> {
            if(b.getMinimumTemperature() < section.getMinimumTemperature() || b.getMinimumTemperature() > section.getMaximumTemperature()){
                throw new ResourceNotFoundException("batch with id " + b.getBatchNumber() + " doesn't belong to the section");
            }
        });

        //validar que la seccion tenga espacio
        if((section.getBatches().size() + inboundOrderRequestDto.getInboundOrder().getBatchStock().size() > section.getMaximumBatchQuantity())){
            throw new ResourceNotFoundException("there isn't space for all new batches");
        }


        InboundOrder inboundOrder = modelMapper.map(inboundOrderRequestDto.getInboundOrder(),InboundOrder.class);

        //mapeo de batchesDTO a batches
        for(int i = 0; i < inboundOrderRequestDto.getInboundOrder().getBatchStock().size();i++){

            BatchDto batchDto = inboundOrderRequestDto.getInboundOrder().getBatchStock().get(i);
            Batch batch = inboundOrder.getBatchStock().get(i);

            mapBatchDtoToBatch(batchDto,batch,section,inboundOrder);

            //validar existan los lotes entrantes
            if(!batchRepository.existsById(batchDto.getBatchNumber())){
                throw new ResourceNotFoundException("Batch with id " + batchDto.getBatchNumber() + " not exist");
            }

            //eliminar lotes que coincidan con los lotes entrantes
            if(batchRepository.existsById(batchDto.getBatchNumber())){
                batchRepository.deleteById(batchDto.getBatchNumber());
            }
        }

        iInboundOrderRepository.save(inboundOrder);

        InboundOrderResponseDto inboundOrderResponseDto = new InboundOrderResponseDto();

        //mapeo de batches batchesDto
        inboundOrderResponseDto.setBatchStock(
                inboundOrder.getBatchStock().stream().map(e -> {
                    int productId = e.getProduct().getId();
                    BatchDto dto = modelMapper.map(e, BatchDto.class);
                    dto.setProductId(productId);
                    return dto;
                }).collect(Collectors.toList()));

        return inboundOrderResponseDto;
    }
}
