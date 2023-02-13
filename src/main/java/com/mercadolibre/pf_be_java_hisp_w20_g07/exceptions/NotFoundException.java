package com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NotFoundException extends RuntimeException{

    public NotFoundException(){

    }
    public NotFoundException(String message) {
        super(message);
    }
}
