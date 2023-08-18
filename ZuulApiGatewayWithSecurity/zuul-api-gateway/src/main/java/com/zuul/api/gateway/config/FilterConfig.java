package com.zuul.api.gateway.config;

import com.zuul.api.gateway.filters.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter());
        registrationBean.addUrlPatterns("/user-service/users/*");
        registrationBean.addUrlPatterns("/student-service/*");
        registrationBean.addUrlPatterns("/course-service/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
