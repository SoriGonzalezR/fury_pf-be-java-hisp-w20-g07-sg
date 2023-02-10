package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    public User(Integer id, String user, String password, Role role) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String user;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;

    @OneToOne(mappedBy = "user")
    private WareHouse wareHouse;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<PurchaseOrder> purchaseOrders;






}
