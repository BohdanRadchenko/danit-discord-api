package com.danit.discord.filters;

import com.danit.discord.constants.Api;
import com.danit.discord.services.AuthJwtService;
import com.danit.discord.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthRefreshJwtFilter extends OncePerRequestFilter {
    private final AuthJwtService authJwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (!request.getServletPath().contains(Api.API_AUTH_REFRESH)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authJwtService.resolveToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = authJwtService.getRefreshUsername(token);
            UserDetails user = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), token, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            log.debug(String.format("Auth refresh filter. %s", ex.getMessage()));
        }

        filterChain.doFilter(request, response);
    }
}