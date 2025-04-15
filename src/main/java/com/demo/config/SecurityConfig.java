package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.service.MyUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserService myUserService;
    
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorize) -> authorize
                // Public APIs that do not require authentication
                .requestMatchers("/api/auth/token/generate").permitAll()    
                .requestMatchers("/api/auth/signup").permitAll()
                .requestMatchers("/api/auth/login").permitAll()  
                .requestMatchers("/api/appointment/create").permitAll()  // public access for appointment creation
                .requestMatchers("/api/doctors").permitAll()  // Make sure you permit all for doctors POST
                // Specific endpoints that require role-based authentication
                .requestMatchers("/api/patient/add").hasAuthority("PATIENT")  // Only PATIENT can add patient details
                .requestMatchers("/api/doctor/{doctorId}/patients").hasAuthority("ROLE_DOCTOR")  // Only DOCTOR can view patients for a doctor
                // Any other endpoint should be restricted (by default)
                .anyRequest().authenticated() // Allow authenticated access to other endpoints
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless session for JWT
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter for token validation
        return http.build();
    }

    @Bean
    AuthenticationProvider getAuth() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passEncoder());
        dao.setUserDetailsService(myUserService);    
        return dao;
    }
    
    @Bean
    BCryptPasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager getAuthManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
