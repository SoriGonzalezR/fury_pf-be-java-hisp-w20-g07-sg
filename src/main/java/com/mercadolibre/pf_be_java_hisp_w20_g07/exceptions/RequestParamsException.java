package com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions;

public class RequestParamsException extends RuntimeException{

    public RequestParamsException() {
        super("invalid requestParams");
    }
}
