package com.example.etutorbackend.service;

import com.example.etutorbackend.exception.UserNotFoundException;
import com.example.etutorbackend.mapper.UserPayloadMapper;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.user.UserPayload;
import com.example.etutorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with id: %d";
    private static final String SUCCESSFULLY_USER_UPDATE_MESSAGE = "Successfully updated user";
    private final UserRepository userRepository;

    public UserPayload findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        return UserPayloadMapper.mapToUserPayload(user);
    }

    public String updateUserById(UserPayload userPayload, Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        existingUser.setFirstName(userPayload.getFirstName());
        existingUser.setLastName(userPayload.getLastName());
        existingUser.setEmail(userPayload.getEmail());
        existingUser.setPhoneNumber(userPayload.getPhoneNumber());
        existingUser.setAddress(userPayload.getAddress());
        existingUser.setCity(userPayload.getCity());

        userRepository.save(existingUser);
        
        return SUCCESSFULLY_USER_UPDATE_MESSAGE;
        
        
    }
}
