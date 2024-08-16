package com.rentease_server.server.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


        try (PrintWriter writer = response.getWriter()) {
            writer.println("{");
            writer.println("  \"type\": \"about:blank\",");
            writer.println("  \"title\": \"Unauthorized\",");
            writer.println("  \"status\": " + HttpServletResponse.SC_UNAUTHORIZED + ",");
            writer.println("  \"detail\": \"" + authException.getMessage() + "\",");
            writer.println("  \"access_denied_reason\": \"Authorization Failure !\"");
            writer.println("}");
        }
    }
}
