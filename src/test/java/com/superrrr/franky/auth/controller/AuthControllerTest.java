package com.superrrr.franky.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superrrr.franky.auth.dto.LoginRequestDto;
import com.superrrr.franky.auth.dto.LoginResponseDto;
import com.superrrr.franky.auth.exception.CredencialesInvalidasException;
import com.superrrr.franky.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void login_WithValidCredentials_Returns200() throws Exception {
        LoginRequestDto request = new LoginRequestDto("admin", "admin123");
        LoginResponseDto response = LoginResponseDto.builder().token("jwt-token").build();

        when(authService.login(request)).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void login_WithInvalidCredentials_Returns401() throws Exception {
        LoginRequestDto request = new LoginRequestDto("admin", "wrong");

        when(authService.login(request)).thenThrow(new CredencialesInvalidasException("Credenciales invalidas"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
