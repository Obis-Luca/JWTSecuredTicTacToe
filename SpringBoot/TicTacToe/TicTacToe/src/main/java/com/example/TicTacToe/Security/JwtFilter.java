package com.example.TicTacToe.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
    throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);

        if(request.getServletPath().contains("/auth") ||
                (authHeader == null || !authHeader.startsWith("Bearer ")))
        {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt;
        final String username;

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        if (validateUser(username)){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(checkTokenValidity(jwt, userDetails))
            {
                UsernamePasswordAuthenticationToken authenticationToken  =new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateUser(String username) {return (username != null && SecurityContextHolder.getContext().getAuthentication() == null); }

    private boolean checkTokenValidity(String jwt, UserDetails userDetails) { return (jwtService.isTokenValid(jwt, userDetails));}


}
