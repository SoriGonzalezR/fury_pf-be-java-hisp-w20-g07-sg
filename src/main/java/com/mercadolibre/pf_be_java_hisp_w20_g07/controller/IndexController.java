package com.mercadolibre.pf_be_java_hisp_w20_g07.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class IndexController {


    //US_1 Representante
    @PostMapping("/fresh-products/inboundorder")
    public ResponseEntity<String> r1_1() {
        String info = "info imortante post /api/v1/fresh-products/inboundorder";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @PutMapping("/fresh-products/inboundorder")
    public ResponseEntity<String> r1_2() {
        String info = "info imortante put /api/v1/fresh-products/inboundorder";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    //US_2 buyer
    @GetMapping("/fresh-products/list")
    public ResponseEntity<String> r2_1() {
        String info = "info imortante get /api/v1/fresh-products/list";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @GetMapping("/fresh-products/list?category={FS, RF, FF}")
    public ResponseEntity<String> r2_2(@RequestParam String category) {
        String info = "info imortante get /api/v1/fresh-products/list?category={FS, RF, FF}";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @PostMapping("/fresh-products/orders")
    public ResponseEntity<String> r2_3(@RequestParam String category) {
        String info = "info imortante post /api/v1/fresh-products/orders";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @GetMapping("/fresh-products/orders/{idOrder}")
    public ResponseEntity<String> r2_4(@PathVariable int idOrder) {
        String info = "info imortante get /api/v1/fresh-products/orders/{idOrder}";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @PutMapping("/fresh-products/orders/{idOrder}")
    public ResponseEntity<String> r2_5(@PathVariable int idOrder) {
        String info = "info imortante put /api/v1/fresh-products/orders/{idOrder}";
        return new ResponseEntity(info, HttpStatus.OK);
    }

    //US_3 Representante





}
