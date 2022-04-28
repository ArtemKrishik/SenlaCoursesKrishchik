package com.github.krishchik.whowithme.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenFilter implements Filter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private ObjectMapper mapper = new ObjectMapper();
    private Map<Object, Object> responseBody;

    public JwtTokenFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(req, res);
            }
        } else {
            responseBody = new HashMap<>();
            responseBody.put("message", "Please sing in");
            mapper.writeValue(res.getWriter(), responseBody);
        }
    }

    @Override
    public void destroy() {

    }
}

