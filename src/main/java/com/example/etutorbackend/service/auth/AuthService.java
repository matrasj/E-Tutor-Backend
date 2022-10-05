package com.example.etutorbackend.service.auth;

import com.example.etutorbackend.exception.UsernameAlreadyExistsException;
import com.example.etutorbackend.model.payload.auth.login.LoginPayloadRequest;
import com.example.etutorbackend.model.payload.auth.login.LoginPayloadResponse;
import com.example.etutorbackend.model.payload.auth.register.RegisterPayloadRequest;
import com.example.etutorbackend.model.payload.auth.register.RegisterPayloadResponse;
import com.example.etutorbackend.repository.ConfirmationTokenRepository;
import com.example.etutorbackend.repository.UserRepository;
import com.example.etutorbackend.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String USERNAME_ALREADY_EXISTS_MESSAGE = "User with username %s already exists";
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenService confirmationTokenService;
    public LoginPayloadResponse loginUser(LoginPayloadRequest request) {
        return applicationUserDetailsService.createJwtToken(request);
    }

    public RegisterPayloadResponse registerUser(RegisterPayloadRequest registerPayloadRequest) {
        if (userRepository.findByUsername(registerPayloadRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(String.format(USERNAME_ALREADY_EXISTS_MESSAGE, registerPayloadRequest.getUsername()));
        }

        return applicationUserDetailsService.registerUser(registerPayloadRequest);
    }

    public String confirmAccountByToken(String token) {
        return confirmationTokenService.confirmAccountWithToken(token);
    }
}
