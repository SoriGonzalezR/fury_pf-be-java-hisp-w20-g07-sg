package com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.ProductDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ProductOrderResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ProductResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.NotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    IProductRepository productRepository;
    IUsersRepository usersRepository;
    IPurchaseOrderRepository purchaseOrderRepository;
    IPurchaseOrderHasProductRepository purchaseOrderHasProductRepository;
    IOrderStatusRepository orderStatusRepository;
    ModelMapper modelMapper = new ModelMapper();

    public ProductServiceImpl(IProductRepository productRepository, IUsersRepository usersRepository, IPurchaseOrderRepository purchaseOrderRepository, IPurchaseOrderHasProductRepository purchaseOrderHasProductRepository, IOrderStatusRepository orderStatusRepository) {
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderHasProductRepository = purchaseOrderHasProductRepository;
        this.orderStatusRepository = orderStatusRepository;
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
        if (!usersRepository.findById(purchaseOrderRequestDTO.getBuyerId()).isPresent()) {
            throw new NotFoundException("buyer doesn't exist");
        }
        User user = usersRepository.findById(purchaseOrderRequestDTO.getBuyerId()).get();

        for (ProductDTO productDTO: productsDTOS) {
            if (productRepository.findCurrentQuantityByProductId(productDTO.getProductId()) < productDTO.getQuantity()) {
                productsIncorrectQuantity.add(productDTO);
            }
        }
        if (!productsIncorrectQuantity.isEmpty()) {
            throw new NotFoundException("These quantities aren't in stock " + productsIncorrectQuantity);
        }
        for (int i = 0; i < productsDTOS.size(); i++) {
            if (purchaseOrderRequestDTO.getDate().isBefore(productRepository.findDueDateByProduct(productsDTOS.get(i).getProductId()).minusWeeks(3))) {
            } else {
                productsDueThreeWeeks.add(productsDTOS.get(i));
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
        if (purchaseOrderRequestDTO.getOrderStatus().getStatusCode().equals("carrito") && usersRepository.findById(purchaseOrderRequestDTO.getBuyerId()).isPresent()) {
            Optional<PurchaseOrder> purchaseOrderToUpdate = purchaseOrderRepository.findById(orderId);
            List<Integer> productIdCurrent = new ArrayList<>();
            //Current List from db
            List<PurchaseOrderHasProduct> purchaseOrderHasProductList = purchaseOrderHasProductRepository.findByPurchaseOrder(orderId);
            if (purchaseOrderToUpdate.isPresent()) {
                User user = usersRepository.findById(purchaseOrderRequestDTO.getBuyerId()).get();
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

}


