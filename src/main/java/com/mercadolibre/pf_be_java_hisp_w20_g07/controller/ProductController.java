package com.mercadolibre.pf_be_java_hisp_w20_g07.controller;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.InboundOrderRequestDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.BatchStockDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.InboundOrderResponseDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.IProductService;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.SesionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /*

    //US_2 buyer

    @GetMapping("/list")
    public ResponseEntity<String> r2__1_2(@RequestParam(required = false) String category) {
        String info = "info imortante get /api/v1/fresh-products/list?category={FS, RF, FF}";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<String> r2_3(@RequestParam String category) {
        String info = "info imortante post /api/v1/fresh-products/orders";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @GetMapping("/orders/{idOrder}")
    public ResponseEntity<String> r2_4(@PathVariable int idOrder) {
        String info = "info imortante get /api/v1/fresh-products/orders/{idOrder}";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @PutMapping("/orders/{idOrder}")
    public ResponseEntity<String> r2_5(@PathVariable int idOrder) {
        String info = "info imortante put /api/v1/fresh-products/orders/{idOrder}";
        return new ResponseEntity(info, HttpStatus.OK);
    }
    */
    //US_3 Representante

    @GetMapping("/{idProduct}/batch/list")
    public ResponseEntity<BatchStockDTO> getProductInStock(@PathVariable Integer idProduct, @RequestParam(required = false) String order, @RequestHeader Map<String, String> headers) {
        String username = SesionServiceImpl.getUsername(headers.get("Authorization").replace("Bearer ",""));
        return new ResponseEntity(productService.productInStock(idProduct, order, username), HttpStatus.OK);
    }


    /*
    //US 4 Representante
    @GetMapping("/{idProduct}/warehouse/list")
    public ResponseEntity<String> r4__1() {
        String info = "info imortante get /api/v1/fresh-products/{idProduct}/warehouse/list";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    //US 5 Representante



    @GetMapping("/batch/list/due-date/{cantDays}")
    public ResponseEntity<String> r5__1_2(
            @PathVariable int cantDayes,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String order
            ) {
        String info = "info imortante get /api/v1/fresh-products/batch/list/due-date/{cantDays}?category = {FS, RF, FF}&order = {date_asc, date_desc}}";
        return new ResponseEntity(info, HttpStatus.OK);
    }*/











}
