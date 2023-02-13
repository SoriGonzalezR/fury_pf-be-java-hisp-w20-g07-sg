package com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
