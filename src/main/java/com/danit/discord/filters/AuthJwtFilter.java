package com.danit.discord.filters;

import com.danit.discord.constants.Api;
import com.danit.discord.controllers.TokenService;
import com.danit.discord.services.AuthJwtService;
import com.danit.discord.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthJwtFilter extends OncePerRequestFilter {

    private final AuthJwtService authJwtService;
    private final UserService userService;
    private final TokenService tokenService;

    private boolean checkAuthRequest(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
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
        if (checkAuthRequest(request, response, filterChain)) return;

        String token = authJwtService.resolveToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("token : " + token);

//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwt = authHeader.substring(7);
//        System.out.println("jwt " + jwt);
//        userEmail = authJwtService.extractUsername(jwt);
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userService.loadUserByUsername(userEmail);
//            var isTokenValid = tokenRepository.findByToken(jwt)
//                    .map(t -> !t.isExpired() && !t.isRevoked())
//                    .orElse(false);
//            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
        filterChain.doFilter(request, response);
    }
}