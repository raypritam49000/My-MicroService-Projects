package com.student.rest.api.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Configuration
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(FeignClientInterceptor.class);

    private HttpServletRequest httpServletRequest;

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = httpServletRequest.getHeader("Authorization");
        LOGGER.info("Feign Client Interceptor : Token :  {} ", token);
        requestTemplate.header("Authorization", token);
    }
}
