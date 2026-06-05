package com.superrrr.franky.auth.service;

import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.auth.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void run_WhenNoUsersExist_ShouldCreateAdminAndUser() {
        when(usuarioRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        dataInitializer.run();

        verify(usuarioRepository, times(2)).save(any(Usuario.class));
    }

    @Test
    void run_WhenUsersExist_ShouldSkip() {
        when(usuarioRepository.count()).thenReturn(1L);

        dataInitializer.run();

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void run_ShouldCreateAdminWithCorrectRole() {
        when(usuarioRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        dataInitializer.run();

        verify(usuarioRepository).save(argThat(u ->
                u.getUsername().equals("admin") && u.getRol() == Rol.ADMIN
        ));
    }

    @Test
    void run_ShouldCreateUserWithCorrectRole() {
        when(usuarioRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        dataInitializer.run();

        verify(usuarioRepository).save(argThat(u ->
                u.getUsername().equals("user") && u.getRol() == Rol.USER
        ));
    }
}
