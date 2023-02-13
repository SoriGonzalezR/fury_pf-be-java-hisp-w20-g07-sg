package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "usuarios")
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "user")
    private WareHouse wareHouse;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<PurchaseOrder> purchaseOrders;






}
