package com.jindero.banking.shared.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    // Konstruktor

    public JwtAuthFilter (JwtService jwtService, CustomUserDetailsService customUserDetailsService){
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        //Získání tokenu
        String token = authHeader.substring(7);

        //Zjištení kdo token vlastní
        String email = jwtService.extractEmail(token);

        //Pokud email existuje a uživatel není přihlášen
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //Načtení uživatele z DB
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            //Ověření platnosti tokenu a zda nevypršel
            boolean isValid = jwtService.validateJwtToken(token);

            if (isValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email,
                        null ,
                        userDetails.getAuthorities()
                );
                // IP a session
                authToken.setDetails(new org.springframework.security.web.authentication
                        .WebAuthenticationDetailsSource()
                        .buildDetails(request));

                //Uživatel přihlášen
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

}
