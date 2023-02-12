package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsernameAndPassword(String userName, String password);

    //@Query("from User as u where u.username = :userName and u.password = :password ")
    //Optional<User> findUser(String userName, String password);

    Optional<User> findUserByUsername(String name);


}
