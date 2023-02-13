package com.mercadolibre.pf_be_java_hisp_w20_g07.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

    public Role(String userRole) {
        this.userRole = userRole;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_role")
    private String userRole;

    //@ManyToOne ToOne(cascade = CascadeType.ALL,mappedBy = "role")

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "role")
    private List<User> user;

    public Role() {

    }
}
