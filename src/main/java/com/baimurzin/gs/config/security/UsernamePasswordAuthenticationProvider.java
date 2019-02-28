package com.baimurzin.gs.config.security;

import com.baimurzin.gs.model.Role;
import com.baimurzin.gs.model.User;
import com.baimurzin.gs.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;


public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserService userService;
    private final TokenService tokenService;

    public UsernamePasswordAuthenticationProvider(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String passwordObj = (String) authentication.getCredentials();

        String email = (String) authentication.getPrincipal();
        if (passwordObj.isEmpty() || email == null || email.isEmpty()) {
            throw new BadCredentialsException("Authentication error.");
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        if (bCryptPasswordEncoder.matches(passwordObj, user.getPassword())) {
            String newToken = tokenService.generateNewToken();

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Role role : user.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            AuthenticationWithToken authToken = new AuthenticationWithToken(user, null,
                    grantedAuthorities);

            authToken.setToken(newToken);

            SecurityContextHolder.getContext().setAuthentication(authToken);
            tokenService.store(newToken, authToken);
            return authToken;
        } else {
            throw new BadCredentialsException("Bad user password or smth");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
