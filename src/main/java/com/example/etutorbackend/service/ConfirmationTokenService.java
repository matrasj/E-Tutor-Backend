package com.example.etutorbackend.service;

import com.example.etutorbackend.exception.ConfirmationTokenAlreadyConfirmedException;
import com.example.etutorbackend.exception.ConfirmationTokenAlreadyExpiredException;
import com.example.etutorbackend.exception.ConfirmationTokenNotFoundException;
import com.example.etutorbackend.model.entity.ConfirmationToken;
import com.example.etutorbackend.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ConfirmationTokenService {
    private static final String CONFIRMATION_TOKEN_NOT_FOUND_MESSAGE = "Not found token %s";
    private static final String CONFIRMATION_TOKEN_ALREADY_CONFIRMED_MESSAGE = "Token %s has been already confirmed";
    private static final String CONFIRMATION_TOKEN_ALREADY_EXPIRED_MESSAGE = "Token %s has already expired";
    private static final boolean ENABLE_ACCOUNT = true;
    private static final String SUCCESSFULLY_TOKEN_CONFIRMATION = "Successfully confirmed account";
    private final ConfirmationTokenRepository confirmationTokenRepository;


    public String confirmAccountWithToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException(String.format(CONFIRMATION_TOKEN_NOT_FOUND_MESSAGE, token)));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenAlreadyConfirmedException(String.format(CONFIRMATION_TOKEN_ALREADY_CONFIRMED_MESSAGE, token));
        }

        if (confirmationToken.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenAlreadyExpiredException(String.format(CONFIRMATION_TOKEN_ALREADY_EXPIRED_MESSAGE, token));
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationToken.getUser().setEnabled(ENABLE_ACCOUNT);

        confirmationTokenRepository.save(confirmationToken);

        return SUCCESSFULLY_TOKEN_CONFIRMATION;
    }
}
