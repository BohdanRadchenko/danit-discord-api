package com.danit.discord.filters;

import com.danit.discord.constants.Api;
import com.danit.discord.services.AuthJwtService;
import com.danit.discord.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.security.SignatureException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthAccessJwtFilter extends GenericFilterBean {
    private final AuthJwtService authJwtService;
    @Lazy
    private final UserService userService;

    private boolean checkAuthRequest(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        boolean isLoginRequest = req.getServletPath().contains(Api.API_AUTH_LOGIN);
        boolean isRegisterRequest = req.getServletPath().contains(Api.API_AUTH_REGISTER);
        boolean isRefreshRequest = req.getServletPath().contains(Api.API_AUTH_REFRESH);
        if (!isLoginRequest && !isRegisterRequest && !isRefreshRequest) return false;
        chain.doFilter(req, res);
        return true;
    }

    @Override
    public void doFilter(
            @NonNull ServletRequest req,
            @NonNull ServletResponse res,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (checkAuthRequest(request, response, filterChain)) return;

        String token = authJwtService.resolveToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails user = null;

        try {
            String username = authJwtService.getAccessUsername(token);
            user = userService.loadUserByUsername(username);

            if (user == null || user.getUsername().isEmpty() || SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), "", user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException ex) {
            log.debug(String.format("Expired jwt exception. %s", ex.getMessage()));
        } catch (SignatureException ex) {
            log.debug(String.format("Signature exception. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.debug(String.format("Exception. %s", ex.getMessage()));
        }

        filterChain.doFilter(request, response);
    }
}