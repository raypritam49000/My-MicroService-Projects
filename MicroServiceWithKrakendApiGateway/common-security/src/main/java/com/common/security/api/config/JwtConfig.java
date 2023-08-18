package com.common.security.api.config;

import com.common.security.api.jsonwebtoken.JsonWebTokenUtility;
import com.common.security.api.security.JsonWebTokenAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JwtConfig {

    @Bean
    @Primary
    public JsonWebTokenUtility getJwt() {
        return new JsonWebTokenUtility();
    }


    @Bean
    @Primary
    public JsonWebTokenAuthenticationProvider getJwtProvider() {
        return new JsonWebTokenAuthenticationProvider();
    }
}
