/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.configs;

import com.lmd.filters.CustomAccessDeniedHandler;
import com.lmd.filters.JwtAuthenticationTokenFilter;
import com.lmd.filters.RestAuthenticationEntryPoint;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author Nome
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.lmd.controller",
    "com.lmd.repository",
    "com.lmd.service",
    "com.lmd.components"})
@Order(1)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.cors();
        http.csrf().ignoringAntMatchers("/api/**");
        http.authorizeRequests().antMatchers("/api/login/").permitAll();
        http.authorizeRequests().antMatchers("/api/restaurants/").permitAll();
        http.authorizeRequests().antMatchers("/api/restaurantOwn/**").permitAll();
        http.authorizeRequests().antMatchers("/api/restaurants/").permitAll();
        http.authorizeRequests().antMatchers("/api/restaurants/**").permitAll();
        http.authorizeRequests().antMatchers("/api/food/").permitAll();
        http.authorizeRequests().antMatchers("/api/food/**").permitAll();
        http.authorizeRequests().antMatchers("/api/foods/").permitAll();
        http.authorizeRequests().antMatchers("/api/foods/**").permitAll();
        http.authorizeRequests().antMatchers("/api/foods/**/updateFood/").permitAll();
        http.authorizeRequests().antMatchers("/api/comments/").permitAll();
        http.authorizeRequests().antMatchers("/api/follows/**").permitAll();
        http.authorizeRequests().antMatchers("/api/orders/**").permitAll();
        http.authorizeRequests().antMatchers("/api/pay/").permitAll();
        http.authorizeRequests().antMatchers("/api/users/**").permitAll();
        http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_RESTAURANT')")
                .antMatchers(HttpMethod.PATCH, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_RESTAURANT')")
                .antMatchers(HttpMethod.POST, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_RESTAURANT')")
                .antMatchers(HttpMethod.DELETE, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_RESTAURANT')").and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
