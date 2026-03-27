package com.example.demo.config;

import org.springframework.http.HttpMethod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
 
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired 
    private JwtAuthFilter jwtAuthFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // (Security Rules) قواعد الدخول
    @Bean // comme @Service pour faire des injections au methods pas class
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{

        http
        .cors(c -> {
          CorsConfigurationSource source = corsConfigurationSource();
          c.configurationSource(source);
        })
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth-> auth
              .requestMatchers("/auth/signup", 
              "/auth/login",
              "/auth/forgot-password/{username}",
              "/auth/reset-password").permitAll()
              .requestMatchers(HttpMethod.GET, "/etudiants").hasRole("ADMIN")
              .requestMatchers(HttpMethod.GET, "/etudiants/{id}").hasAnyRole("ADMIN", "USER")
              .requestMatchers(HttpMethod.POST, "/etudiants").hasAnyRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/etudiants/{id}").hasAnyRole("ADMIN")
              .requestMatchers(HttpMethod.PUT, "/etudiants/{id}").hasAnyRole("ADMIN", "USER")
              .requestMatchers(HttpMethod.POST, "/etudiants/{id}/leave-requests").hasAnyRole("ADMIN", "USER")
              .requestMatchers(HttpMethod.GET, "/etudiants/{id}/leave-requests").hasAnyRole("ADMIN", "USER")
              .anyRequest()
              .authenticated() 
    )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationManager(authenticationManager(http));
       
        return http.build();

    }


    //هاد هو المدير لي كايقرر واش المستخدم اللي دخل صحيح ولا لا
    @Bean
  AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    var authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    return authBuilder.build();
  }


  // Utilisation de CORS
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:5173")); //5173 = Port de Frontend
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}