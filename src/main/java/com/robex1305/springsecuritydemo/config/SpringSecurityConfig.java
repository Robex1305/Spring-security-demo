package com.robex1305.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.ClientAuthorizationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.*;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder(){
        if(this.passwordEncoder == null){
            this.passwordEncoder = new BCryptPasswordEncoder();
        }
        return this.passwordEncoder;
    }

    private String encode(String token){
        return passwordEncoder().encode(token);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password(encode("user")).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(encode("admin")).roles("ADMIN");
    }


    @Override
    public void configure(HttpSecurity req) throws Exception{
        req.exceptionHandling().accessDeniedPage("/unauthorized")
                .and().formLogin().loginProcessingUrl("/login").defaultSuccessUrl("/home?from=login")
                .and().oauth2Login().defaultSuccessUrl("/home?from=login")
                .and().logout().logoutSuccessUrl("/home?from=logout");
    }
}
