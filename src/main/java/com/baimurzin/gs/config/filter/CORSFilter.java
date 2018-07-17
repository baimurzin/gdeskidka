package com.baimurzin.gs.config.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //  response.addHeader("Access-Control-Allow-Origin", "ttp://logaclhost:5435,http://localhost:25435");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*, Origin, X-Requested-With, Content-Type, Accept, token, Access-Control-Request-Headers, " +
                "Access-Control-Request-Method, DNT, Host, Origin, Connection, ");
        filterChain.doFilter(request, response);
    }

}
