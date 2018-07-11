package com.baimurzin.gs.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public static final String TOKEN_SESSION_KEY = "token";
    public static final String USER_SESSION_KEY = "user";

    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);

        Optional<String> username = Optional.ofNullable(httpRequest.getHeader("X-Auth-Username"));
        Optional<String> password = Optional.ofNullable(httpRequest.getHeader("X-Auth-Password"));
        Optional<String> token = Optional.ofNullable(httpRequest.getHeader("X-Auth-Token"));

        if (!username.isPresent() || !password.isPresent()) {
            username = Optional.ofNullable(httpRequest.getParameter("username"));
            password = Optional.ofNullable(httpRequest.getParameter("password"));
        }

        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

        try {

            if (postToAuthenticate(httpRequest, resourcePath)) {
                logger.debug("Trying to authenticate user {} by X-Auth-Username method", username);
                processUsernamePasswordAuthentication(httpResponse, username, password);
                return;
            }

            if (token != null) {
                logger.debug("Trying to authenticate user by X-Auth-Token method. Token: {}", token);
                processTokenAuthentication(token);
            }

            logger.debug("AuthenticationFilter is passing request down the filter chain");
            addSessionContextToLogging();
            filterChain.doFilter(request, response);
        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", internalAuthenticationServiceException);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        }finally {
            MDC.remove(TOKEN_SESSION_KEY);
            MDC.remove(USER_SESSION_KEY);
        }

    }

    private void addSessionContextToLogging() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String tokenValue = "EMPTY";
        if (authentication != null && !StringUtils.isBlank(authentication.getDetails().toString())) {
            tokenValue = bCryptPasswordEncoder.encode(authentication.getDetails().toString());
        }
        MDC.put(TOKEN_SESSION_KEY, tokenValue);

        String userValue = "EMPTY";
        if (authentication != null && !StringUtils.isBlank(authentication.getPrincipal().toString())) {
            userValue = authentication.getPrincipal().toString();
        }
        MDC.put(USER_SESSION_KEY, userValue);
    }

    private boolean postToAuthenticate(HttpServletRequest httpRequest, String resourcePath) {
        return resourcePath.contains("login") && httpRequest.getMethod().equals("POST");
    }

    private void processUsernamePasswordAuthentication(HttpServletResponse httpResponse, Optional<String> username, Optional<String> password) throws IOException {
        Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        TokenResponse tokenResponse = new TokenResponse(resultOfAuthentication.getDetails().toString());
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(Optional<String> username, Optional<String> password) {
        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        return tryToAuthenticate(requestAuthentication);
    }

    private void processTokenAuthentication(Optional<String> token) {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticateWithToken(Optional<String> token) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        logger.debug("User successfully authenticated");
        return responseAuthentication;
    }

    private static <T> T coalesce(T... items) {
        for (T i : items) if (i != null) return i;
        return null;
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }
}
