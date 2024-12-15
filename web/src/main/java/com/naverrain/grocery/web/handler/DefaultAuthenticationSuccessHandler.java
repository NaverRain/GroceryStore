package com.naverrain.grocery.web.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        response.sendRedirect(targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        var authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("DELETE"))){
            return "/admin";
        }
        else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("WRITE"))){
            return "/manager";
        }
        else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("READ"))){
            return "/";
        }
        return "/";
    }
}
