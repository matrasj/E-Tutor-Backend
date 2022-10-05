package com.example.etutorbackend.service;

import com.example.etutorbackend.exception.UserNotFoundException;
import com.example.etutorbackend.mapper.UserAuthPayloadResponseMapper;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.auth.login.UserAuthPayloadResponse;
import com.example.etutorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserAuthPayloadResponse findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("SDF"));

        return UserAuthPayloadResponseMapper.mapToUserAuthPayloadResponse(user);
    }
}
