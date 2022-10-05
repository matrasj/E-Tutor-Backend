package com.example.etutorbackend.config;


import com.example.etutorbackend.service.auth.ApplicationUserDetailsService;
import com.example.etutorbackend.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");

        String token = null;
        String usernameFromToken = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring("Bearer ".length());

            try {
                usernameFromToken = jwtUtil.getUsernameFromToken(token);
            } catch (IllegalArgumentException illegalArgumentException) {
                log.error("Invalid JWT token");
            } catch (ExpiredJwtException expiredJwtException) {
                if (!request.getServletPath().contains("api/v1/auth")) {
                    log.error("Token has been already expired");
                }
            } catch (Exception exception) {
                log.error("Error");
            }
        } else {
            if (!request.getServletPath().contains("api/v1/auth")) {
                log.error("Token should start with Bearer");
            }
        }

        if (usernameFromToken != null) {
            UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(usernameFromToken);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                                userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
