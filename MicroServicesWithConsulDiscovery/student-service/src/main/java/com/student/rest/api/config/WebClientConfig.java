package com.student.rest.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;


@Configuration
public class WebClientConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().filter((request, next) -> {
            String token = httpServletRequest.getHeader("Authorization");
            LOGGER.info("Web Client interceptor: Token :  {} ", token);

            ClientRequest newRequest = ClientRequest.create(request.method(), request.url())
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .build();

            return next.exchange(newRequest);
        });
    }


    @Bean
    public WebClient webClient() {
        return webClientBuilder().build();
    }

}
