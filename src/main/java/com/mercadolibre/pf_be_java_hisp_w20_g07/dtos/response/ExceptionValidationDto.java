package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionValidationDto {

    String name;
    List<String> erros;
}
