package com.rentease_server.server.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWTAuthenticationFilter is responsible for filtering incoming requests
 * and setting the security context if a valid JWT token is provided.
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTHelper jwtHelper;  // Helper class for handling JWT token-related operations

    @Autowired
    private UserDetailsService userDetailsService;  // Service to load user-specific data

    /**
     * Filters incoming requests and sets the authentication in the security context
     * if a valid JWT token is provided in the Authorization header.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException if an input or output error is detected
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Retrieve the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        // If the Authorization header is empty or doesn't start with "Bearer ", continue with the filter chain
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header
        jwt = authHeader.substring(7);
        // Extract the username from the JWT token
        userName = jwtHelper.extractUserName(jwt);

        // If the username is not empty and the security context doesn't already have an authenticated user
        if (StringUtils.isNoneEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the UserDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            // Validate the JWT token
            if (jwtHelper.isTokenValid(jwt, userDetails)) {
                // Create a new security context and set the authentication token
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                // Create an authentication token with the user's details and authorities
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // Set additional details for the authentication token
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the security context with the authenticated user
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

}
