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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetailsWhenFound() {
        Usuario usuario = Usuario.builder()
                .username("testuser")
                .password("encoded-pass")
                .rol(Rol.ADMIN)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();

        when(usuarioRepository.findByUsernameAndEstadoUsuarioNot("testuser", EstadoUsuario.ELIMINADO))
                .thenReturn(Optional.of(usuario));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encoded-pass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_ShouldThrowWhenNotFound() {
        when(usuarioRepository.findByUsernameAndEstadoUsuarioNot("unknown", EstadoUsuario.ELIMINADO))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("unknown"));
    }
}
