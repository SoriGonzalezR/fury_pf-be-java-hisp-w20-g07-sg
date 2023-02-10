package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserRequestDTO {

    @JsonAlias("userName")
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
