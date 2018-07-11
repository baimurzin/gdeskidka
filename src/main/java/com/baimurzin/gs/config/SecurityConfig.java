package com.baimurzin.gs.config;

import com.baimurzin.gs.config.stateless.JWTAuthenticationFilter;
import com.baimurzin.gs.config.stateless.RestAuthenticationEntryPoint;
import com.baimurzin.gs.config.stateless.TokenAuthenticationProvider;
import com.baimurzin.gs.config.stateless.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Assert;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@ComponentScan(basePackages = {"com.baimurzin.gs.config.stateless"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("domainAuthProvider")
    private AuthenticationProvider authenticationProvider;

//    private final UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/company/**").hasRole("CUSTOMER")
                .antMatchers("/customer/**").hasRole("ADMIN")

//                .antMatchers("/resources/**", "/registration").permitAll()
//                .antMatchers("/admin1/**").hasRole("ADMIN")
//                .antMatchers("/company/**").authenticated()
//                .antMatchers("/customer/**").hasRole("CUSTOMER")
//                .antMatchers("/**").anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());


        JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        authenticationFilter.setBCryptPasswordEncoder(bCryptPasswordEncoder());

        http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        Assert.notNull(userDetailsService, "UserDetailsService must not be null");
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(authenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider());
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenService tokenService() {
        return new TokenService();
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        TokenAuthenticationProvider tokenAuthenticationProvider = new TokenAuthenticationProvider();
        tokenAuthenticationProvider.setTokenService(tokenService());
        return tokenAuthenticationProvider;
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
}
