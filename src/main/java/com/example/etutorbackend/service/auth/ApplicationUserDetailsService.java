package com.example.etutorbackend.service.auth;

import com.example.etutorbackend.exception.UserNotFoundException;
import com.example.etutorbackend.mapper.UserAuthPayloadResponseMapper;
import com.example.etutorbackend.model.entity.ConfirmationToken;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.auth.login.LoginPayloadRequest;
import com.example.etutorbackend.model.payload.auth.login.LoginPayloadResponse;
import com.example.etutorbackend.model.payload.auth.register.RegisterPayloadRequest;
import com.example.etutorbackend.model.payload.auth.register.RegisterPayloadResponse;
import com.example.etutorbackend.repository.ConfirmationTokenRepository;
import com.example.etutorbackend.repository.UserRepository;
import com.example.etutorbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationUserDetailsService implements UserDetailsService {
    private final MailService mailService;
    private static final String USERNAME_NOT_FOUND_MESSAGE = "Not found user --> %s";
    private static final String BAD_CREDENTIALS_MESSAGE = "Bad credentials";
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with username --> %s";
    private static final String USER_DISABLED_MESSAGE = "User with username --> %s is disabled";
    private static final int MINUTES_TIME_DURATION = 15;
    private static final boolean DISABLED_ACCOUNT = false;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


//    @PostConstruct
//    public void addUsers() {
//        User user = User.builder()
//                .firstName("Kuba")
//                .lastName("Matras")
//                .username("pozerinhooo")
//                .password(passwordEncoder.encode("Czajnik13!"))
//                .email("jkob.matras@gmail.com")
//                .enabled(true)
//                .build();
//
//        userRepository.save(user);
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, username)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities("ROLE_USER")
                .build();
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


    public RegisterPayloadResponse registerUser(RegisterPayloadRequest registerPayloadRequest) {
        User user = buildUserFromRegisterPayload(registerPayloadRequest);

        ConfirmationToken confirmationToken = ConfirmationTokenFactory.createConfirmationToken();
        confirmationToken.setUser(user);

        user.getConfirmationTokens().add(confirmationToken);

        userRepository.save(user);
        confirmationTokenRepository.save(confirmationToken);

        mailService.sendConfirmationEmail(user, confirmationToken);

        return RegisterPayloadResponse.builder()
                .confirmationToken(confirmationToken.getToken())
                .minutesDuration(MINUTES_TIME_DURATION)
                .expiresAt(confirmationToken.getExpireAt())
                .build();

    }

    private User buildUserFromRegisterPayload(RegisterPayloadRequest registerPayloadRequest) {
        return User.builder()
                .firstName(registerPayloadRequest.getFirstName())
                .lastName(registerPayloadRequest.getLastName())
                .username(registerPayloadRequest.getUsername())
                .password(passwordEncoder.encode(registerPayloadRequest.getPassword()))
                .email(registerPayloadRequest.getEmail())
                .enabled(DISABLED_ACCOUNT)
                .confirmationTokens(new HashSet<>())
                .build();

    }
}
