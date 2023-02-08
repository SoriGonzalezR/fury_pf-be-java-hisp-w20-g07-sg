package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter @Setter
@Entity
@Table(name = "warehouse")
public class WareHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

    private String pais;
    private String ciudad;
    private String direccion;

    //mappear
    private Usuario usuario;


}
