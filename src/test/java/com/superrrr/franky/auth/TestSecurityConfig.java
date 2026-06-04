package com.superrrr.franky.auth;

import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.auth.repositories.UsuarioRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public PasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static Usuario createTestAdmin(PasswordEncoder encoder) {
        return Usuario.builder()
                .id(1L)
                .username("admin")
                .password(encoder.encode("admin123"))
                .rol(Rol.ADMIN)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();
    }

    public static Usuario createTestUser(PasswordEncoder encoder) {
        return Usuario.builder()
                .id(2L)
                .username("user")
                .password(encoder.encode("user123"))
                .rol(Rol.USER)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();
    }
}
