package com.naverrain.grocery.web.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("DELETE"))){
            response.sendRedirect("/admin");
        }
        else if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("WRITE"))){
            response.sendRedirect("/manager");
        }
        else {
            response.sendRedirect("/");
        }

    }
}
