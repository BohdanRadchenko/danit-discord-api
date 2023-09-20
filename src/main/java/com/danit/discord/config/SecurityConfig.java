package com.danit.discord.config;

import com.danit.discord.constants.Api;
import com.danit.discord.filters.AuthAccessJwtFilter;
import com.danit.discord.filters.AuthRefreshJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final AuthAccessJwtFilter authJwtFilter;
    private final AuthRefreshJwtFilter authRefreshJwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers(mvcMatcherBuilder.pattern(Api.API_AUTH_REFRESH)).authenticated()
                )
                .addFilterBefore(authRefreshJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .accessDeniedHandler(
                                        (request, response, accessDeniedException) -> response
                                                .sendError(HttpStatus.FORBIDDEN.value())
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers(mvcMatcherBuilder.pattern(Api.API_AUTH_LOGIN)).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern(Api.API_AUTH_REGISTER)).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api-docs")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api-docs/**")).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .accessDeniedHandler(
                                        (request, response, accessDeniedException) -> response
                                                .sendError(HttpStatus.UNAUTHORIZED.value())
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                );

        return http.build();
    }
}