package com.naverrain.grocery.web.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        if (email == null) {
            email = oauth2User.getAttribute("userPrincipalName");
        }
        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Set<SimpleGrantedAuthority> authorities = loadUserAuthorities(email);

        if (oauth2User instanceof OidcUser oidcUser) {
            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), "email");
        }

        return new DefaultOAuth2User(authorities, oauth2User.getAttributes(), "email");
    }


    private Set<SimpleGrantedAuthority> loadUserAuthorities(String email) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        if (email.equalsIgnoreCase("admin@example.com")) {
            authorities.add(new SimpleGrantedAuthority("DELETE"));
            authorities.add(new SimpleGrantedAuthority("WRITE"));
            authorities.add(new SimpleGrantedAuthority("READ"));
        }
        else if (email.equalsIgnoreCase("manager@example.com")) {
            authorities.add(new SimpleGrantedAuthority("WRITE"));
            authorities.add(new SimpleGrantedAuthority("READ"));
        }
        else {
            authorities.add(new SimpleGrantedAuthority("READ"));
        }

        return authorities;
    }
}
