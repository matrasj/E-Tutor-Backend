package com.example.etutorbackend.service.auth;

import com.example.etutorbackend.exception.UserNotFoundException;
import com.example.etutorbackend.model.entity.UserPrincipal;
import com.example.etutorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user %s";
    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }
}
