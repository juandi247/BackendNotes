package com.prueba.juandiego.config;


import com.prueba.juandiego.jwt.JwtAuthenticationFilter;
import jakarta.servlet.Filter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;


    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception
{

    return http
            .csrf(csrf-> csrf.disable())
            .authorizeHttpRequests(authRequest ->
                authRequest
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                        )
            .sessionManagement(sessionManager -> sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();

}



}
