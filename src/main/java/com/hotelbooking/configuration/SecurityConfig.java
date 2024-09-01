package com.hotelbooking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	  http
          .csrf(csrf -> csrf.disable()) // Disable CSRF protection
          .authorizeHttpRequests(authz -> authz
              .requestMatchers("/login").permitAll() // Allow access to the login page without authentication
              .anyRequest().permitAll() // Allow access to all other requests without authentication
          )
          .formLogin(form -> form
              .loginPage("/login") // Specify the login page
              .permitAll() // Allow access to the login page without authentication
          )
          .logout(logout -> logout
              .permitAll() // Allow access to logout without authentication
          );

      return http.build();
 }
 
}
