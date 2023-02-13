package com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions;

public class UnAuthorizeException extends RuntimeException {
    public UnAuthorizeException(){
        super("User y/o contraseña incorrecto");
    }
}
