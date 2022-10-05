package com.example.etutorbackend.service;

import com.example.etutorbackend.exception.UserNotFoundException;
import com.example.etutorbackend.mapper.UserAuthPayloadResponseMapper;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.auth.LoginPayloadRequest;
import com.example.etutorbackend.model.payload.auth.LoginPayloadResponse;
import com.example.etutorbackend.repository.UserRepository;
import com.example.etutorbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationUserDetailsService implements UserDetailsService {
    private static final String USERNAME_NOT_FOUND_MESSAGE = "Not found user --> %s";
    private static final String BAD_CREDENTIALS_MESSAGE = "Bad credentials";
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with username --> %s";
    private static final String USER_DISABLED_MESSAGE = "User with username --> %s is disabled";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostConstruct
    public void addUsers() {
        User user = User.builder()
                .username("pozerinhooo")
                .password(passwordEncoder.encode("Czajnik13!"))
                .email("jkob.matras@gmail.com")
                .build();

        userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, username)));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public LoginPayloadResponse createJwtToken(LoginPayloadRequest jwtRequest) {
        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        authenticate(username, password);

        final UserDetails userDetails = loadUserByUsername(username);
        String generatedToken = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));


        return LoginPayloadResponse.builder()
                .jwtToken(generatedToken)
                .userAuthPayloadResponse(UserAuthPayloadResponseMapper.mapToUserAuthPayloadResponse(user))
                .build();
    }



    public void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException exception) {
            log.error("User is disabled");
            throw new DisabledException(String.format(USER_DISABLED_MESSAGE, username));
        } catch (BadCredentialsException badCredentialsException) {
            log.error("Bard credentials from user");
            throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
        }
    }



}
