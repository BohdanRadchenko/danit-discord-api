package com.danit.discord.filters;

import com.danit.discord.constants.Api;
import com.danit.discord.controllers.TokenService;
import com.danit.discord.services.AuthJwtService;
import com.danit.discord.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthJwtFilter extends OncePerRequestFilter {
    private final AuthJwtService authJwtService;
    private final UserService userService;
    private final TokenService tokenService;

    private boolean checkLoginOrRegisterRequest(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        boolean isLoginRequest = req.getServletPath().contains(Api.API_AUTH_LOGIN);
        boolean isRegisterRequest = req.getServletPath().contains(Api.API_AUTH_REGISTER);
        if (!isLoginRequest && !isRegisterRequest) return false;
        chain.doFilter(req, res);
        return true;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (checkLoginOrRegisterRequest(request, response, filterChain)) return;

        String token = authJwtService.resolveToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;

        try {
            username = authJwtService.getAccessUsername(token);
        } catch (ExpiredJwtException ex) {
            log.debug(String.format("Expired jwt exception. %s", ex.getMessage()));
        } catch (SignatureException ex) {
            log.debug(String.format("Signature exception. %s", ex.getMessage()));
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}