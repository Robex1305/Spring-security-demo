/**package com.capgemini.springsecuritydemo.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginPageFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(request.getRequestURI().equalsIgnoreCase("/login") && isAuthenticated()){
            response.sendRedirect("/home?from=login");
            response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
**/