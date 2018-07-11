package com.baimurzin.gs.config.stateless;

import com.baimurzin.gs.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

@Setter
@Getter
public class JWTAuthenticationFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public static final String TOKEN_SESSION_KEY = "token";
    public static final String USER_SESSION_KEY = "user";

    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = asHttp(servletRequest);
        HttpServletResponse response = asHttp(servletResponse);

        String login = coalesce(request.getHeader("X-Auth-Username"), servletRequest.getParameter("email"));
        String password = coalesce(request.getHeader("X-Auth-Password"), servletRequest.getParameter("password"));
        String token = coalesce(request.getHeader("X-Auth-Token"), servletRequest.getParameter("token"));

        String resourcePath = new UrlPathHelper().getPathWithinApplication(request);

        try {

            if (postToAuthenticate(request, resourcePath)) {
                logger.debug("Trying to authenticate user {} by X-Auth-Username method", login);
                processUsernamePasswordAuthentication(response, login, password);
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
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
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

    private void processUsernamePasswordAuthentication(HttpServletResponse httpResponse, String username, String password) throws IOException {
        Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        User principal = (User) resultOfAuthentication.getPrincipal();
        TokenResponse tokenResponse = new TokenResponse(resultOfAuthentication.getDetails().toString());
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(String username, String password) {
        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username, password);
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

    private void processTokenAuthentication(String token) {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticateWithToken(String token) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
        return tryToAuthenticate(requestAuthentication);
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
