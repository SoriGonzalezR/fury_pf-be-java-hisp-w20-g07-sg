package com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("User y/o contraseña incorrecto");
    }

}
