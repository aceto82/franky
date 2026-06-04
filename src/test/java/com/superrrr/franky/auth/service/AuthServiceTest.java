package com.superrrr.franky.auth.service;

import com.superrrr.franky.auth.dto.LoginRequestDto;
import com.superrrr.franky.auth.dto.LoginResponseDto;
import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.auth.exception.CredencialesInvalidasException;
import com.superrrr.franky.auth.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_ShouldReturnTokenWithValidCredentials() {
        Usuario usuario = Usuario.builder()
                .username("testuser")
                .password("encoded-pass")
                .rol(Rol.USER)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();
        LoginRequestDto request = LoginRequestDto.builder()
                .username("testuser")
                .password("raw-pass")
                .build();

        when(usuarioRepository.findByUsernameAndEstadoUsuarioNot("testuser", EstadoUsuario.ELIMINADO))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("raw-pass", "encoded-pass")).thenReturn(true);
        when(jwtTokenProvider.generateToken(usuario)).thenReturn("jwt-token");

        LoginResponseDto response = authService.login(request);

        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void login_ShouldThrowWhenUserNotFound() {
        LoginRequestDto request = LoginRequestDto.builder()
                .username("unknown")
                .password("pass")
                .build();

        when(usuarioRepository.findByUsernameAndEstadoUsuarioNot("unknown", EstadoUsuario.ELIMINADO))
                .thenReturn(Optional.empty());

        assertThrows(CredencialesInvalidasException.class, () -> authService.login(request));
    }

    @Test
    void login_ShouldThrowWhenPasswordDoesNotMatch() {
        Usuario usuario = Usuario.builder()
                .username("testuser")
                .password("encoded-pass")
                .rol(Rol.USER)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();
        LoginRequestDto request = LoginRequestDto.builder()
                .username("testuser")
                .password("wrong-pass")
                .build();

        when(usuarioRepository.findByUsernameAndEstadoUsuarioNot("testuser", EstadoUsuario.ELIMINADO))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrong-pass", "encoded-pass")).thenReturn(false);

        assertThrows(CredencialesInvalidasException.class, () -> authService.login(request));
    }
}
