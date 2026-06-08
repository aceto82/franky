package com.superrrr.franky.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superrrr.franky.auth.dto.LoginRequestDto;
import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.auth.repositories.UsuarioRepository;
import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import com.superrrr.franky.producto.repositories.ProductoRepository;
import com.superrrr.franky.sucursal.entity.Sucursal;
import com.superrrr.franky.sucursal.enums.EstadoSucursal;
import com.superrrr.franky.sucursal.repositories.SucursalRepository;
import com.superrrr.franky.venta.repositories.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

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

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Long sucursalId;
    private Long productoId;

    @BeforeEach
    void setUp() {
        ventaRepository.deleteAll();
        usuarioRepository.deleteAll();
        sucursalRepository.deleteAll();
        productoRepository.deleteAll();

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

        Sucursal sucursal = sucursalRepository.save(Sucursal.builder()
                .nombre("Test Sucursal")
                .direccion("Test Dir")
                .estadoSucursal(EstadoSucursal.ACTIVO)
                .build());
        sucursalId = sucursal.getId();

        Producto producto = productoRepository.save(Producto.builder()
                .nombre("Test Producto")
                .precio(BigDecimal.TEN)
                .estadoProducto(EstadoProducto.ACTIVO)
                .build());
        productoId = producto.getId();
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
    void createVenta_WithUserRole_Returns201() throws Exception {
        Usuario user = usuarioRepository
                .findByUsernameAndEstadoUsuarioNot("testuser", EstadoUsuario.ELIMINADO)
                .orElseThrow();
        String token = TestJwtHelper.generateToken(user);

        String ventaJson = """
                {
                    "sucursalId": %d,
                    "detalle": [
                        {"productoId": %d, "cantidad": 2}
                    ]
                }
                """.formatted(sucursalId, productoId);

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + token)
                        .header("Idempotency-Key", "int-test-key-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ventaJson))
                .andExpect(status().isCreated());
    }

    @Test
    void getProductoMasVendido_WithValidToken_Returns200() throws Exception {
        Usuario admin = usuarioRepository
                .findByUsernameAndEstadoUsuarioNot("testadmin", EstadoUsuario.ELIMINADO)
                .orElseThrow();
        String token = TestJwtHelper.generateToken(admin);

        String ventaJson = """
                {
                    "sucursalId": %d,
                    "detalle": [
                        {"productoId": %d, "cantidad": 2}
                    ]
                }
                """.formatted(sucursalId, productoId);

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + token)
                        .header("Idempotency-Key", "int-test-key-2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ventaJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/estadisticas/producto-mas-vendido")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Producto"))
                .andExpect(jsonPath("$.totalVendido").value(2));
    }

    @Test
    void getProductoMasVendido_WithoutToken_Returns401() throws Exception {
        mockMvc.perform(get("/api/estadisticas/producto-mas-vendido"))
                .andExpect(status().isUnauthorized());
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
