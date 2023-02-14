package com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl;


import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.RequestParamsException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.ResourceNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.UserNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.ProductDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.NotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {


    

    IWarehouseRepository warehouseRepository;

    ISectionRepository sectionRepository;

    IInboundOrderRepository iInboundOrderRepository;

    IBatchRepository batchRepository;
    
    IProductRepository productRepository;
    IUserRepository userRepository;
    IPurchaseOrderRepository purchaseOrderRepository;
    IPurchaseOrderHasProductRepository purchaseOrderHasProductRepository;
    IOrderStatusRepository orderStatusRepository;
    ModelMapper modelMapper = new ModelMapper();


    public ProductServiceImpl(IWarehouseRepository warehouseRepository, ISectionRepository sectionRepository, IInboundOrderRepository iInboundOrderRepository, IBatchRepository batchRepository, IProductRepository productRepository, IUserRepository userRepository, IPurchaseOrderRepository purchaseOrderRepository, IPurchaseOrderHasProductRepository purchaseOrderHasProductRepository, IOrderStatusRepository orderStatusRepository) {
        this.warehouseRepository = warehouseRepository;
        this.sectionRepository = sectionRepository;
        this.iInboundOrderRepository = iInboundOrderRepository;
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderHasProductRepository = purchaseOrderHasProductRepository;
        this.orderStatusRepository = orderStatusRepository;
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


            //validar que no existan los lotes entrantes
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
    
    @Override
    public List<ProductResponseDTO> getProducts() {
        return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(String code) {
        List<ProductResponseDTO> productResponseDTOS = productRepository.findProductByCategory(code).stream().map(product -> modelMapper.map(product, ProductResponseDTO.class)).collect(Collectors.toList());
        if (productResponseDTOS.isEmpty()) {
            throw new NotFoundException("No products");
        }
        return productResponseDTOS;
    }

    @Override
    public PurchaseOrderResponseDTO createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        List<ProductDTO> productsDTOS = purchaseOrderRequestDTO.getProduct();
        List<PurchaseOrderHasProduct> purchaseOrderHasProductList = new ArrayList<>();
        List<ProductDTO> productsIncorrectQuantity = new ArrayList<>();
        List<ProductDTO> productsDueThreeWeeks = new ArrayList<>();
        if (!userRepository.findById(purchaseOrderRequestDTO.getBuyerId()).isPresent()) {
            throw new NotFoundException("buyer doesn't exist");
        }
        User user = userRepository.findById(purchaseOrderRequestDTO.getBuyerId()).get();

        for (ProductDTO productDTO: productsDTOS) {
            if (productRepository.findCurrentQuantityByProductId(productDTO.getProductId()) < productDTO.getQuantity()) {
                productsIncorrectQuantity.add(productDTO);
            }
        }
        if (!productsIncorrectQuantity.isEmpty()) {
            throw new NotFoundException("These quantities aren't in stock " + productsIncorrectQuantity);
        }
        List<Batch> batchWithProducts = batchRepository.findByProductId(purchaseOrderRequestDTO.getProduct().get(0).getProductId());
        for (Batch batch: batchWithProducts) {
            if (purchaseOrderRequestDTO.getDate().isBefore(batch.getDueDate().minusWeeks(3))) {
            } else {
                productsDueThreeWeeks.add(new ProductDTO(batch.getProduct().getId(),batch.getCurrentQuantity()));
            }
        }
        if (!productsDueThreeWeeks.isEmpty()) {
            throw new RuntimeException("These date will due soon");
        }
        OrderStatus orderStatus = orderStatusRepository.findByStatus(purchaseOrderRequestDTO.getOrderStatus().getStatusCode());
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setDate(purchaseOrderRequestDTO.getDate());
        purchaseOrder.setOrderStatus(orderStatus);
        purchaseOrder.setUser(user);
        purchaseOrderRepository.save(purchaseOrder);
        int sizeL = purchaseOrderRequestDTO.getProduct().size();
        int count = 0;
        Double sumTotal = 0.0;
        Double priceByProduct = 0.0;
        while (count < sizeL) {
            PurchaseOrderHasProduct purchaseOrderHasProduct = new PurchaseOrderHasProduct();
            purchaseOrderHasProduct.setPurchaseOrder(purchaseOrder);
            Product product = productRepository.findById(purchaseOrderRequestDTO.getProduct().get(count).getProductId()).get();
            purchaseOrderHasProduct.setProduct(product);
            purchaseOrderHasProduct.setQuantity(purchaseOrderRequestDTO.getProduct().get(count).getQuantity());
            purchaseOrderHasProductList.add(purchaseOrderHasProduct);
            priceByProduct = purchaseOrderHasProduct.getProduct().getPrice() * purchaseOrderHasProduct.getQuantity();
            sumTotal = priceByProduct + sumTotal;
            count++;
        }
        purchaseOrderHasProductRepository.saveAll(purchaseOrderHasProductList);
        return new PurchaseOrderResponseDTO(sumTotal);
    }

    @Override
    public Double calculateTotalPrice() {
        return null;
    }

    @Override
    public List<ProductOrderResponseDTO> getOrder(int orderId) {
        System.out.println(productRepository.findProductByOrderId(orderId));
        return productRepository.findProductByOrderId(orderId).stream().map(product -> modelMapper.map(product, ProductOrderResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public String updateOrder(int orderId, PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        if (purchaseOrderRequestDTO.getOrderStatus().getStatusCode().equals("carrito") && userRepository.findById(purchaseOrderRequestDTO.getBuyerId()).isPresent()) {
            Optional<PurchaseOrder> purchaseOrderToUpdate = purchaseOrderRepository.findById(orderId);
            List<Integer> productIdCurrent = new ArrayList<>();
            //Current List from db
            List<PurchaseOrderHasProduct> purchaseOrderHasProductList = purchaseOrderHasProductRepository.findByPurchaseOrder(orderId);
            if (purchaseOrderToUpdate.isPresent()) {
                User user = userRepository.findById(purchaseOrderRequestDTO.getBuyerId()).get();
                System.out.println(purchaseOrderToUpdate.get().getDate());
                purchaseOrderToUpdate.get().setDate(purchaseOrderRequestDTO.getDate());
                purchaseOrderToUpdate.get().setUser(user);
                System.out.println(purchaseOrderToUpdate.get().getUser().getId());
                purchaseOrderRepository.save(purchaseOrderToUpdate.get());
            }
            int sizeCurrent = purchaseOrderRequestDTO.getProduct().size();
            int sizeLast = purchaseOrderHasProductList.size();
            int count = 0;
            if (sizeLast > sizeCurrent) {
                for (int i = 0; i < sizeCurrent; i++) {
                    productIdCurrent.add(purchaseOrderRequestDTO.getProduct().get(i).getProductId());
                }
                List<Integer> list = purchaseOrderHasProductRepository.findProductIdByOrderId(orderId).stream().filter(f -> !productIdCurrent.contains(f)).collect(Collectors.toList());
                for (int j = 0; j < list.size(); j++) {
                    purchaseOrderHasProductRepository.deleteByProductIdAndPurchaseOrderId(list.get(j), orderId);

                }
            } else if (sizeLast <= sizeCurrent) {
                for (int i = 0; i < sizeCurrent; i++) {
                    if (i < sizeLast) {
                        if (purchaseOrderHasProductList.get(i).getProduct().getId() != purchaseOrderRequestDTO.getProduct().get(i).getProductId() || purchaseOrderHasProductList.get(i).getQuantity() != purchaseOrderRequestDTO.getProduct().get(i).getQuantity()) {
                            Product product = productRepository.findById(purchaseOrderRequestDTO.getProduct().get(i).getProductId()).get();
                            purchaseOrderHasProductList.get(i).setProduct(product);
                            purchaseOrderHasProductList.get(i).setQuantity(purchaseOrderRequestDTO.getProduct().get(i).getQuantity());
                        }
                    }
                }
                count = sizeLast;
                while (count < sizeCurrent) {
                    PurchaseOrderHasProduct purchaseOrderHasProduct = new PurchaseOrderHasProduct();
                    purchaseOrderHasProduct.setPurchaseOrder(purchaseOrderToUpdate.get());
                    Product product = productRepository.findById(purchaseOrderRequestDTO.getProduct().get(count).getProductId()).get();
                    purchaseOrderHasProduct.setPurchaseOrder(purchaseOrderToUpdate.get());
                    purchaseOrderHasProduct.setProduct(product);
                    purchaseOrderHasProduct.setQuantity(purchaseOrderRequestDTO.getProduct().get(count).getQuantity());
                    purchaseOrderHasProductList.add(purchaseOrderHasProduct);
                    count++;
                }
                purchaseOrderHasProductRepository.saveAll(purchaseOrderHasProductList);
            }
        }
        return "update";
    }

    @Override
    public FindBatchesDueToExpireSoonDto findBatchesDueToExpireSoon(int days, String userName) {

        int idWarehouse = userRepository.findUserByUsername(userName).orElseThrow(() -> new UserNotFoundException()).getWareHouse().getId();

        LocalDate startDate = LocalDate.parse("2023-02-14");
        LocalDate endDate = startDate.plusDays(days);

        List<Batch> batches = batchRepository.findBatches(idWarehouse,startDate,endDate);

        FindBatchesDueToExpireSoonDto result = new FindBatchesDueToExpireSoonDto();
        result.setBatchStock(batches.stream().map( e -> modelMapper.map(e,BatchDto.class)).collect(Collectors.toList()));
        return result;
    }

    @Override
    public FindBatchesDueToExpireSoonDto findBatchesDueToExpireSoon(int days, String order, String category, String userName) {

        if((!category.equals("FS") && !category.equals("RF") && !category.equals("FF")) || (!order.equals("date_asc") && !order.equals("date_desc"))){
            throw new RequestParamsException();
        }
        int idWarehouse = userRepository.findUserByUsername(userName).orElseThrow(() -> new UserNotFoundException()).getWareHouse().getId();

        LocalDate startDate = LocalDate.parse("2023-02-14");

        LocalDate endDate = startDate.plusDays(days);
        List<Batch> batches;
        if( order.equals("date_asc")) {
            batches = batchRepository.findBatchesAsc(idWarehouse,startDate,endDate,category);
        } else {
            batches = batchRepository.findBatchesDesc(idWarehouse,startDate,endDate,category);
        }

        FindBatchesDueToExpireSoonDto result = new FindBatchesDueToExpireSoonDto();
        result.setBatchStock(batches.stream().map( e -> modelMapper.map(e,BatchDto.class)).collect(Collectors.toList()));

        return result;
    }
}


