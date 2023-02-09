package com.mercadolibre.pf_be_java_hisp_w20_g07.service;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.UserRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.UserResponseDTO;

public interface ISesionService {

    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @param user -- user con userName y password
     * @return UserResponseDTO -- Objeto con userName y token
     * @throws UserNotFoundException -- se lanza la exception en caso de no encontrar al usuario
     */
    UserResponseDTO login(UserRequestDTO user );
}
