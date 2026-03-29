package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Autowired
  private JwtHelper jwtHelper;
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    System.out.println("FILTER IS RUNNING....");
    //aller au headers at creer key Authorisation + Barear (token) dans value
    String authHeader = request.getHeader("Authorization");
    String token = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) { //Vérifie si token existe
      token = authHeader.substring(7); //Supprime Bearer pour garder seulement token
    }
    if (token == null) {
      filterChain.doFilter(request, response); //continue sans authentification.
      return;
    }
    String username = jwtHelper.extractUsername(token);

    if (username != null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      boolean isTokenValid = jwtHelper.isTokenValid(token, userDetails);
      if (isTokenValid) {

        
        var authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,//pour credentials pas important
            userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); //Spring considère maintenant que user est authentifié.
      }
    }

    filterChain.doFilter(request, response);
  }
}
