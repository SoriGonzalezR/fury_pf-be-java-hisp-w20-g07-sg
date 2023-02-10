package com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl;

import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.UserRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.UserResponseDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.User;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.UserNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.repository.UserRepository;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.ISesionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.AuthorityUtils;
import io.jsonwebtoken.Jwts;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mercadolibre.pf_be_java_hisp_w20_g07.util.CONSTANTS.SECRET_KEY_TOKEN;

@Service
public class SesionServiceImpl implements ISesionService {

    UserRepository userRepository;
    public SesionServiceImpl () {
        this.userRepository = new UserRepository();
    }

    @Override
    public UserResponseDTO login (UserRequestDTO user ) {
        //Voy a la base de datos y reviso que el usuario y contraseña existan.
        // ToDo: se podria agregar alguna libreria para encriptar la password
        String username = user.getUserName();
        User usuario = userRepository.findByUsernameAndPassword(username, user.getPassword())
                .orElseThrow(UserNotFoundException::new);


        List<String> roles = new ArrayList<>();
        //roles = usuario.getRoles();
        //        .stream()
        //        .map(e -> e.getRol().getText())
        //        .collect(Collectors.toList());
        roles.add(usuario.getRole().getUserRole());


        String token = getJWTToken(username, roles);

        return new UserResponseDTO(username, token);
    }

    /**
     * Genera un token para un usuario específico, válido por 10'
     *
     * @param username - user to login
     * @param roles    - collection of user's roles
     * @return String
     */
    /* lista de roles */
    private String getJWTToken ( String username, List<String> roles ) {

        List<GrantedAuthority> grantedAuthorities = roles
                .stream()
                .map(AuthorityUtils::commaSeparatedStringToAuthorityList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        LocalDateTime expired = LocalDateTime.now()
                .plusMinutes(30);
        Date expiredTime = Date.from(expired.atZone(ZoneId.systemDefault())
                .toInstant());

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiredTime)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY_TOKEN.getBytes())
                .compact();

        return "Bearer " + token;
    }

    /**
     * Decodifica un token para poder obtener los componentes que contiene el mismo
     *
     * @param token tokenJWT
     * @return Claims
     */
    /*private static Claims decodeJWT (String token ) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY_TOKEN.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }*/

    /**
     * Permite obtener el username según el token indicado
     *
     * @param token token JWT
     * @return String User's Email
     */
    /*public static String getUsername ( String token ) {
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }*/
}
