package com.zuul.api.gateway.filters;

import com.common.security.utility.JsonWebTokenUtility;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends GenericFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        LOGGER.info("@@@ Inside JwtAuthenticationFilter ::: ");

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {

            final String authHeader = request.getHeader("Authorization");

            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            }

            if (authHeader == null) {
                throw new ServletException("JWT is Required !!!");
            }

            if (!authHeader.startsWith("Bearer ")) {
                throw new ServletException("Please add valid JWT in header !!!");
            }

            final String token = authHeader.substring(7);

            String username = JsonWebTokenUtility.extractUsername(token);

            if (Objects.nonNull(username)) {
                filterChain.doFilter(request, response);
            }

        } catch (IOException | ServletException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

}
