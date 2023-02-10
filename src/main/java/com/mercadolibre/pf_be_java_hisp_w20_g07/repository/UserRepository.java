package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Role;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepository {

    List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
        users.add(new User(1,"Tomas","tomas123",new Role("REPRESENTANTE")));
        users.add(new User(2,"Manuel","manuel123",new Role("BUYER")));
        users.add(new User(3,"Soraya","Soraya123",new Role("REPRESENTANTE")));
        users.add(new User(4,"Nathalia","Nathalia123",new Role("BUYER")));
    }

    public Optional<User> findByUsernameAndPassword(String username, String password){
        Optional<User> user = users.stream().filter(e -> e.getUsername().equals(username) && e.getPassword().equals(password)).findFirst();
        return user;
    }
}
