package com.mercadolibre.pf_be_java_hisp_w20_g07.controller;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.UserRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.UserResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.ISesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SessionController {

    @Autowired
    ISesionService service;

    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @param user - user con userName y password
     * @return UserResponseDTO
     */
    @PostMapping("/log-in")
    public UserResponseDTO login(@RequestBody UserRequestDTO user ) {
        System.out.println(user);
        return service.login(user);
    }
}
