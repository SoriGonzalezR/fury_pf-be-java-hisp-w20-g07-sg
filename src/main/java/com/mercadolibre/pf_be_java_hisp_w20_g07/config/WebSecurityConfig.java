package com.mercadolibre.pf_be_java_hisp_w20_g07.config;


import com.mercadolibre.pf_be_java_hisp_w20_g07.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Configuración para excluir end-points
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http.csrf()
          .disable()
          .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()

                //US1
                .antMatchers("/api/v1/fresh-products/inboundorder")
                .hasAnyAuthority("REPRESENTANTE")
                //US2
                .antMatchers("/api/v1/fresh-products/list","/api/v1/fresh-products/orders","/api/v1/fresh-products/orders/{idOrder}")
                .hasAnyAuthority("BUYER","REPRESENTANTE")
                //US3
                .antMatchers("/api/v1/fresh-products/{idProduct}/batch/list")
                .hasAnyAuthority("REPRESENTANTE")
                //US4
                .antMatchers("/api/v1/fresh-products/{idProduct}/warehouse/list")
                .hasAnyAuthority("REPRESENTANTE")
                //US5
                .antMatchers("/api/v1/fresh-products/batch/list/due-date/{cantDays}")
                .hasAnyAuthority("REPRESENTANTE")

                .antMatchers(HttpMethod.POST, "/api/v1/log-in")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/ping")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/fake")
                .permitAll()
                .anyRequest()
                .permitAll();
    }

    /**
     * Configuración para excluir paginas
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure ( WebSecurity web ) throws Exception {
        web.ignoring()
          .antMatchers("/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**");

        web.ignoring()
          .antMatchers(
            "/h2-console/**");
    }
}
