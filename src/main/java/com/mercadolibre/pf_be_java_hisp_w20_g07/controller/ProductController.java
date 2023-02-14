package com.mercadolibre.pf_be_java_hisp_w20_g07.controller;


import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.*;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IProductService;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.SesionServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

    IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    //US_1 Representante
    @PostMapping("/inboundorder")
    public ResponseEntity<InboundOrderResponseDto> r1__1(
            @RequestBody InboundOrderRequestDto inboundOrderRequestDto,
            @RequestHeader Map<String, String> headers){

        String username = SesionServiceImpl.getUsername(headers.get("Authorization").replace("Bearer ",""));
        return new ResponseEntity(productService.save(inboundOrderRequestDto,username), HttpStatus.OK);
    }

    @PutMapping("/inboundorder")
    public ResponseEntity<InboundOrderResponseDto> r1__2(
            @RequestBody InboundOrderRequestDto inboundOrderRequestDto,
            @RequestHeader Map<String, String> headers){

        String username = SesionServiceImpl.getUsername(headers.get("Authorization").replace("Bearer ",""));
        return new ResponseEntity(productService.update(inboundOrderRequestDto,username), HttpStatus.OK);
    }
    //US_2 buyer

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponseDTO>> findProductByCategory(@RequestParam(required = false) String code) {
        if (code == null) {
            return new ResponseEntity(productService.getProducts(), HttpStatus.OK);
        }
        return new ResponseEntity(productService.getProductsByCategory(code), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrderResponseDTO> createOrder(@RequestBody PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        return new ResponseEntity(productService.createPurchaseOrder(purchaseOrderRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<List<ProductOrderResponseDTO>> findProductsByOrder(@PathVariable int orderId) {
        return new ResponseEntity(productService.getOrder(orderId), HttpStatus.OK);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable int orderId, @RequestBody PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        return new ResponseEntity(productService.updateOrder(orderId, purchaseOrderRequestDTO), HttpStatus.OK);
    }
/*
    //US_3 Representante

    @GetMapping("/{idProduct}/batch/list")
    public ResponseEntity<String> r3__1_2(@RequestParam(required = false) String idOrder) {
        String info = "info imortante get /api/v1//api/v1/fresh-products/{idProduct}/batch/list?order={L, C, F}";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    //US 4 Representante
    @GetMapping("/{idProduct}/warehouse/list")
    public ResponseEntity<String> r4__1() {
        String info = "info imortante get /api/v1/fresh-products/{idProduct}/warehouse/list";
        return new ResponseEntity(info, HttpStatus.OK);
    }
*/
    //US 5 Representante

    @GetMapping("/batch/list/due-date/{cantDays}")
    public ResponseEntity<FindBatchesDueToExpireSoonDto> r5__1_1(
            @PathVariable Integer cantDays,
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String order
            ){

        String username = SesionServiceImpl.getUsername(headers.get("Authorization").replace("Bearer ",""));

        if(category == null && order == null) {
            return new ResponseEntity(productService.findBatchesDueToExpireSoon(cantDays, username), HttpStatus.OK);
        } else {
            return new ResponseEntity(productService.findBatchesDueToExpireSoon(cantDays, order,category,username), HttpStatus.OK);
        }
    }
}
