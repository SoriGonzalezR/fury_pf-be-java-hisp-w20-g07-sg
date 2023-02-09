package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class UserRequestDTO {

    @JsonAlias("user_name")
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
