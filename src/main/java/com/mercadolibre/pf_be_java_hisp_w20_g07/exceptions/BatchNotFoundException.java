package com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions;

public class BatchNotFoundException extends RuntimeException{
    public BatchNotFoundException(String message) {
        super(message);
    }
}
