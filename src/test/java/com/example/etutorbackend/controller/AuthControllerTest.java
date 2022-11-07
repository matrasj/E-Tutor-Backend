package com.example.etutorbackend.controller;

import com.example.etutorbackend.jwt.JwtTokenProvider;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.entity.UserPrincipal;
import com.example.etutorbackend.model.payload.auth.login.LoginPayloadRequest;
import com.example.etutorbackend.repository.UserRepository;
import com.example.etutorbackend.service.UserService;
import com.example.etutorbackend.service.auth.ApplicationUserDetailsService;
import com.example.etutorbackend.service.auth.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor
class AuthControllerTest {
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthService authService;

    @MockBean
    private ApplicationUserDetailsService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @AfterEach
    void tearDown() {
        Mockito.reset(userService);
    }

    @Test
    void register() {
    }

    @Test
    void login() throws Exception {
        User user = getUser();

        assertAll("Properties test",
                () -> assertEquals("pozerinhooo", user.getUsername() , "Username failed"),
                () -> assertEquals("password", user.getPassword(), "Password failed"),
                () -> assertEquals("jkob.matras@gmail.com", user.getEmail(), "Mail failed")
        );

        var loginRequest = new LoginPayloadRequest("pozerinhooo", "password");
        UserDetails userDetails = new UserPrincipal(user);
        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);

        given(authenticationManager.authenticate(any()))
                .willReturn(
                        new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword()));

        mockMvc
                .perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginRequestJson)
                                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void confirmAccount() {
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .username("pozerinhooo")
                .password("password")
                .phoneNumber("admin")
                .email("jkob.matras@gmail.com")
                .firstName("Admin")
                .lastName("Admin")
                .build();
    }
}