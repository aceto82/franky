package com.superrrr.franky.auth.service;

import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private static final String SECRET = "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2";
    private static final long EXPIRATION = 3600000L;

    private JwtTokenProvider tokenProvider;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider(SECRET, EXPIRATION);
        usuario = Usuario.builder()
                .id(1L)
                .username("testuser")
                .password("encoded-pass")
                .rol(Rol.ADMIN)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();
    }

    @Test
    void generateToken_ShouldReturnValidJwt() {
        String token = tokenProvider.generateToken(usuario);

        assertNotNull(token);
        assertFalse(token.isBlank());
        assertTrue(token.split("\\.").length == 3);
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        String token = tokenProvider.generateToken(usuario);

        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void validateToken_ShouldReturnFalseForTamperedToken() {
        String token = tokenProvider.generateToken(usuario);
        String tampered = token.substring(0, token.length() - 5) + "XXXXX";

        assertFalse(tokenProvider.validateToken(tampered));
    }

    @Test
    void validateToken_ShouldReturnFalseForMalformedToken() {
        assertFalse(tokenProvider.validateToken("not-a-jwt"));
    }

    @Test
    void validateToken_ShouldReturnFalseForEmptyString() {
        assertFalse(tokenProvider.validateToken(""));
    }

    @Test
    void getUsernameFromToken_ShouldReturnSubject() {
        String token = tokenProvider.generateToken(usuario);

        assertEquals("testuser", tokenProvider.getUsernameFromToken(token));
    }

    @Test
    void getRolFromToken_ShouldReturnRolClaim() {
        String token = tokenProvider.generateToken(usuario);

        assertEquals("ADMIN", tokenProvider.getRolFromToken(token));
    }
}
