package com.example.etutorbackend.service.auth;

import com.example.etutorbackend.exception.InvalidEmailException;
import com.example.etutorbackend.exception.InvalidPasswordException;
import com.example.etutorbackend.exception.UserNotFoundException;
import com.example.etutorbackend.exception.UsernameAlreadyExistsException;
import com.example.etutorbackend.factories.ConfirmationTokenFactory;
import com.example.etutorbackend.jwt.JwtTokenProvider;
import com.example.etutorbackend.mapper.UserPayloadMapper;
import com.example.etutorbackend.model.entity.ConfirmationToken;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.auth.login.LoginPayloadRequest;
import com.example.etutorbackend.model.payload.auth.login.LoginPayloadResponse;
import com.example.etutorbackend.model.payload.auth.register.RegisterPayloadRequest;
import com.example.etutorbackend.model.payload.auth.register.RegisterPayloadResponse;
import com.example.etutorbackend.repository.ConfirmationTokenRepository;
import com.example.etutorbackend.repository.RoleRepository;
import com.example.etutorbackend.repository.UserRepository;
import com.example.etutorbackend.service.ConfirmationTokenService;
import com.example.etutorbackend.validation.EmailValidator;
import com.example.etutorbackend.validation.PasswordValidator;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String USERNAME_ALREADY_EXISTS_MESSAGE = "User with username %s already exists";
    private static final String BAD_CREDENTIALS = "Bad credentials";
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with username %s";
    private static final String INVALID_EMAIL_MESSAGE = "Email %s is invalid";
    private static final boolean USER_DISABLED = false;
    private final ConfirmationTokenService confirmationTokenService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    public LoginPayloadResponse loginUser(LoginPayloadRequest request)  {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new com.example.etutorbackend.exception.AccessDeniedException(BAD_CREDENTIALS));

            String generatedJwtToken = jwtTokenProvider.generateJwtToken(authenticate);

            return LoginPayloadResponse.builder()
                    .userPayload(UserPayloadMapper.mapToUserPayload(user))
                    .jwtToken(generatedJwtToken)
                    .build();

        } catch (AuthenticationException authenticationException) {
            throw new com.example.etutorbackend.exception.AccessDeniedException(BAD_CREDENTIALS);
        }
    }

    @Transactional
    public RegisterPayloadResponse registerUser(RegisterPayloadRequest registerPayloadRequest) {
        validateUserData(registerPayloadRequest);

        ConfirmationToken confirmationToken
                = ConfirmationTokenFactory.createConfirmationToken();

        User user = User.builder()
                .firstName(registerPayloadRequest.getFirstName())
                .lastName(registerPayloadRequest.getLastName())
                .username(registerPayloadRequest.getUsername())
                .password(passwordEncoder.encode(registerPayloadRequest.getPassword()))
                .email(registerPayloadRequest.getEmail())
                .confirmationTokens(Collections.singleton(confirmationToken))
                .enabled(USER_DISABLED)
                .build();

        confirmationToken.setUser(user);

        userRepository.save(user);
        confirmationTokenRepository.save(confirmationToken);

        mailService.sendConfirmationEmail(user, confirmationToken);

        return RegisterPayloadResponse.builder()
                .confirmationToken(confirmationToken.getToken())
                .expiresAt(confirmationToken.getExpireAt())
                .minutesDuration(confirmationToken.getMinutesDuration())
                .build();
    }

    private void validateUserData(RegisterPayloadRequest registerPayloadRequest) {
        if (userRepository.findByUsername(registerPayloadRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(String.format(USERNAME_ALREADY_EXISTS_MESSAGE, registerPayloadRequest.getUsername()));
        }

        if (!emailValidator.test(registerPayloadRequest.getEmail())) {
            throw new InvalidEmailException(String.format(INVALID_EMAIL_MESSAGE, registerPayloadRequest.getEmail()));
        }

        if (!passwordValidator.test(registerPayloadRequest.getPassword())) {
            throw new InvalidPasswordException(String.format(INVALID_EMAIL_MESSAGE, registerPayloadRequest.getEmail()));
        }
    }

    public User getCurrentUser() {
        String principal = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(principal)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, principal)));
    }


    public String confirmAccountByToken(String token) {
        return confirmationTokenService.confirmAccountWithToken(token);
    }
}
