package com.superrrr.franky.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superrrr.franky.auth.dto.LoginRequestDto;
import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.auth.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        Usuario admin = Usuario.builder()
                .username("testadmin")
                .password(passwordEncoder.encode("admin123"))
                .rol(Rol.ADMIN)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();
        usuarioRepository.save(admin);

        Usuario user = Usuario.builder()
                .username("testuser")
                .password(passwordEncoder.encode("user123"))
                .rol(Rol.USER)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();
        usuarioRepository.save(user);
    }

    @Test
    void login_WithValidCredentials_ReturnsToken() throws Exception {
        LoginRequestDto request = new LoginRequestDto("testadmin", "admin123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void login_WithInvalidPassword_Returns401() throws Exception {
        LoginRequestDto request = new LoginRequestDto("testadmin", "wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_WithNonExistentUser_Returns401() throws Exception {
        LoginRequestDto request = new LoginRequestDto("nonexistent", "password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getProductos_WithoutToken_Returns401() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getProductos_WithValidToken_Returns200() throws Exception {
        Usuario admin = usuarioRepository
                .findByUsernameAndEstadoUsuarioNot("testadmin", EstadoUsuario.ELIMINADO)
                .orElseThrow();
        String token = TestJwtHelper.generateToken(admin);

        mockMvc.perform(get("/api/productos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void createProducto_WithUserRole_Returns403() throws Exception {
        Usuario user = usuarioRepository
                .findByUsernameAndEstadoUsuarioNot("testuser", EstadoUsuario.ELIMINADO)
                .orElseThrow();
        String token = TestJwtHelper.generateToken(user);

        String productoJson = """
                {
                    "nombre": "Test Producto",
                    "categoria": "TEST",
                    "precio": 10.5
                }
                """;

        mockMvc.perform(post("/api/productos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoJson))
                .andExpect(status().isForbidden());
    }

    @Test
    void createProducto_WithAdminRole_Returns201() throws Exception {
        Usuario admin = usuarioRepository
                .findByUsernameAndEstadoUsuarioNot("testadmin", EstadoUsuario.ELIMINADO)
                .orElseThrow();
        String token = TestJwtHelper.generateToken(admin);

        String productoJson = """
                {
                    "nombre": "Test Producto",
                    "categoria": "TEST",
                    "precio": 10.5
                }
                """;

        mockMvc.perform(post("/api/productos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoJson))
                .andExpect(status().isCreated());
    }
}
