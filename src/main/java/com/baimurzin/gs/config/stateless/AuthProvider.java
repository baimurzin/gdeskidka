package com.baimurzin.gs.config.stateless;

import com.baimurzin.gs.model.Role;
import com.baimurzin.gs.model.User;
import com.baimurzin.gs.service.UserService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component("domainAuthProvider")
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object passwordObj = authentication.getCredentials();
        if (passwordObj == null) {
            passwordObj = "";
        }

        String rawPassword = passwordObj.toString();
        String email = authentication.getName();
        if (rawPassword.isEmpty() || email == null || email.isEmpty()) {
            throw new BadCredentialsException("Authentication error.");
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        if (bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
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
