package com.danit.discord.config;

import com.danit.discord.constants.Api;
import com.danit.discord.filters.AuthJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final AuthJwtFilter authJwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers(mvcMatcherBuilder.pattern(Api.API_AUTH_REGISTER)).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern(Api.API_AUTH_LOGIN)).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/graphiql**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/graphql")).permitAll()
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}