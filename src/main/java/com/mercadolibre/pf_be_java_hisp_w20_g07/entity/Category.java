package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    List<Section> section;
}
