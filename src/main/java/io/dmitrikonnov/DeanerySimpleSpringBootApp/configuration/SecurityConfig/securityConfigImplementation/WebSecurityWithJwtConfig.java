package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration.SecurityConfig.securityConfigImplementation;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.JWTvers2.JwtRequestFilter;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.JWTvers2.JwtUsernameAndPasswordAuthenticationFilter;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Component;



@Component ("webSecurityWithJwtConfig")
@AllArgsConstructor (onConstructor_ ={@Autowired})
@Order(102)
@Lazy
public class WebSecurityWithJwtConfig extends WebSecurityConfigurerAdapter {

    private static final String[]  RESOURCES = new String[]{
            "/", "/home","/pictureCheckCode","/include/**", "/actuator/**",
            "/css/**","/icons/**","/images/**","/js/**","/layer/**"};

    @Value("${jwt.token.secretKey}") private final String secretKey;
    private final DaoAuthenticationProvider daoAuthenticationProvider;



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), secretKey))
                .addFilterAfter(new JwtRequestFilter(secretKey), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(RESOURCES).permitAll()
                .antMatchers("/api/v*/users/**").hasAnyRole("ADMIN","SUPERADMIN")
                .antMatchers("/api/v*/topic/**").hasAnyRole("USER","ADMIN", "SUPERADMIN")
                .antMatchers("/api/v*/course/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                .antMatchers("/api/v*/registration/**").permitAll()
                .anyRequest()
                .authenticated();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider);
    }


}








