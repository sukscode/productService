package dev.sukriti.productservice.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                     //   .requestMatchers("/messages/**").access(hasScope("message:read"))
                        .requestMatchers("/products").hasAuthority("ADMIN")
                        .requestMatchers("/products/{id}").permitAll()//-->Authorize for all except signup
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(
                        jwtConfigurer -> {
                            jwtConfigurer.jwtAuthenticationConverter(new CustomJwtAuthenticationConverter());
                        }
                ))
                .cors().disable()
                .csrf().disable()
        // Form login handles the redirect to the login page from the
        // authorization server filter chain
        ;
        return http.build();
    }
}
