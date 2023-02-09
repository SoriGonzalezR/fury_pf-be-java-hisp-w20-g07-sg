package com.mercadolibre.pf_be_java_hisp_w20_g07.config;

import com.fury.api.FuryUtils;
import com.fury.api.exceptions.FuryDecryptException;
import com.fury.api.exceptions.FuryNotFoundAPPException;
import com.fury.api.exceptions.FuryUpdateException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


@Configuration
@Profile({"test","prod"})
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() throws FuryDecryptException, FuryUpdateException, FuryNotFoundAPPException {

        /*String host = System.getenv("SECRET_HOST_SPRINTF") + ":6612";
        String db = System.getenv("SECRET_DB_SPRINTF");
        String username = System.getenv("SECRET_USERNAME_SPRINTF");
        String password = System.getenv("SECRET_CLAVE_SPRINTF");*/

        String host = System.getenv("DB_HOST");
        String db = System.getenv("SCHEMA_NAME");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        String url = "jdbc:mysql://" + host + "/" + db + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=FALSE"; // Nombre del schema";

        return DataSourceBuilder.create()
                .url(url)
                .password(password)
                .username(username) // Username seg. snippets
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
