package com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.SectionDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.BatchProductDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.BatchStockDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.BatchNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.ResourceNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.UserNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IProductService;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IWarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    IWarehouseService warehouseService;

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
        WareHouse wareHouse = warehouseService.findById(inboundOrderRequestDto.getInboundOrder().getSection().getWarehouseCode())
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
        WareHouse wareHouse = warehouseService.findById(inboundOrderRequestDto.getInboundOrder().getSection().getWarehouseCode())
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


            //eliminar lotes que coincidan con los lotes entrantes
            if(batchRepository.existsById(batchDto.getBatchNumber())){
                batchRepository.deleteById(batchDto.getBatchNumber());
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
    public BatchStockDTO productInStock(Integer idProduct, String order, String username){

        //validar existenia del producto
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Product with id " + idProduct +" not found"));

        //validacion representante-warehouse valido
        User user = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);

        Integer idWarehouse = user.getWareHouse().getId();

        // Validar que el warehouse es válido y que el representante pertenece al  warehouse
        List<Batch> batchList =  batchRepository.findBatchByProduct(idProduct)
                .stream()
                .filter(batch -> batch.getSection().getWarehouse().getId().equals(idWarehouse))
                .filter(batch -> batch.getCurrentQuantity() > 0)
                .collect(Collectors.toList());



        Integer idSection = batchRepository.findSectionByWarehouseAndProduct(idWarehouse, idProduct);

        //
        SectionDto sectionDto = new SectionDto();
        sectionDto.setSectionCode(idSection);
        sectionDto.setWarehouseCode(idWarehouse);

        // BatchStockDTO Data
        BatchStockDTO batchStockDTO = new BatchStockDTO();


        batchStockDTO.setSection(sectionDto);
        batchStockDTO.setProductId(idProduct);



        List<BatchProductDTO> batchProductDTOList = new ArrayList<>();

        for (Batch b: batchList) {
            // BatchProductDTO
            BatchProductDTO batchProductDTO = new BatchProductDTO();

            batchProductDTO.setBatch_number(b.getBatchNumber());
            batchProductDTO.setCurrent_quantity(b.getCurrentQuantity());
            batchProductDTO.setDue_date(b.getDueDate());

            LocalDate dueDate = b.getDueDate();
            LocalDate currentDate = LocalDate.now();

            // Validar fecha de vencimiento
            if (currentDate.isBefore(dueDate.minusWeeks(3))) batchProductDTOList.add(batchProductDTO);

        }

        if (batchProductDTOList.isEmpty()) {
            throw new BatchNotFoundException("Product not avilable");
        }

        // DATA SIN ORDER
        if (order == null){
            batchStockDTO.setBatchStock(batchProductDTOList);

            // CON ORDER
        } else {
            if (order.equals("L")){
                Comparator<BatchProductDTO> compareByBatch = Comparator
                        .comparing(BatchProductDTO::getBatch_number);

                List<BatchProductDTO>  listByBatch = batchProductDTOList.stream().sorted(compareByBatch).collect(Collectors.toList());
                batchStockDTO.setBatchStock(listByBatch);


            } else if (order.equals("C")) {
                Comparator<BatchProductDTO> compareByQuantity = Comparator
                        .comparing(BatchProductDTO::getCurrent_quantity);
                List<BatchProductDTO>  listByQuantity = batchProductDTOList.stream().sorted(compareByQuantity).collect(Collectors.toList());
                batchStockDTO.setBatchStock(listByQuantity);

            } else if (order.equals("F")) {
                Comparator<BatchProductDTO> compareByDueDate = Comparator
                        .comparing(BatchProductDTO::getDue_date);


                List<BatchProductDTO>  listByDueDate = batchProductDTOList.stream().sorted(compareByDueDate).collect(Collectors.toList());
                batchStockDTO.setBatchStock(listByDueDate);

            } else {
                throw new BatchNotFoundException("Invalid order");
            }

        }

        return batchStockDTO;
    }
}
