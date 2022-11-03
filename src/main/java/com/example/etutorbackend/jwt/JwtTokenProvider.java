package com.example.etutorbackend.jwt;

import com.example.etutorbackend.exception.JwtTokenException;
import com.example.etutorbackend.model.entity.Authority;
import com.example.etutorbackend.model.entity.UserPrincipal;
import com.example.etutorbackend.service.auth.ApplicationUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String INVALID_TOKEN_MESSAGE = "Token already expires or is invalid";
    @Value("${auth.jwt.secret-key:}")
    private String secretKey;

    @Value("${auth.jwt.expiration-ms:}")
    private Long validityInMs;

    private final ApplicationUserDetailsService userDetailsService;

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public String generateJwtToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());

        claims.put(
                "auth",
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","))
        );

        return Jwts.builder()
                .setSubject(claims.getSubject())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            throw new JwtTokenException(INVALID_TOKEN_MESSAGE);
        }
    }

    public String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception exception) {
            throw new JwtTokenException(INVALID_TOKEN_MESSAGE);
        }
    }

    // get to know why it is required
    @Transactional
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails," ", userDetails.getAuthorities());
    }

}
