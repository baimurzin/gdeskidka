package com.baimurzin.gs.config.stateless;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Setter @Getter
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = (String) authentication.getPrincipal();
        if (token == null || token.isEmpty()) {
            throw new BadCredentialsException("Invalid token");
        }
        if (!tokenService.contains(token)) {
            throw new BadCredentialsException("Invalid token or token expired");
        }
        return tokenService.retrieve(token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}