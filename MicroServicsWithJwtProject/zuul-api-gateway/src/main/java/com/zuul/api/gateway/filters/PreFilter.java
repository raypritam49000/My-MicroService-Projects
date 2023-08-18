package com.zuul.api.gateway.filters;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreFilter extends ZuulFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        LOGGER.info("@@@ Inside Pre Filter ::: ");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        LOGGER.info("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());
        return null;
    }
}