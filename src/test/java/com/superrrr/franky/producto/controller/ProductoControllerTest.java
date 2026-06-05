package com.superrrr.franky.producto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superrrr.franky.auth.TestJwtHelper;
import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.producto.dto.ProductoRequestDto;
import com.superrrr.franky.producto.dto.ProductoResponseDto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import com.superrrr.franky.producto.exception.ProductoNoEncontradoException;
import com.superrrr.franky.producto.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String adminToken;

    @BeforeEach
    void setUp() {
        adminToken = TestJwtHelper.generateToken(
                Usuario.builder().username("admin").rol(Rol.ADMIN).estadoUsuario(EstadoUsuario.ACTIVO).build()
        );
        when(userDetailsService.loadUserByUsername("admin"))
                .thenReturn(new User("admin", "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
    }

    @Test
    void obtenerProductos_ShouldReturn200() throws Exception {
        List<ProductoResponseDto> productos = List.of(
                ProductoResponseDto.builder().id(1L).nombre("P1").precio(BigDecimal.TEN).build()
        );
        when(productoService.obtenerProductos()).thenReturn(productos);

        mockMvc.perform(get("/api/productos")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("P1"));
    }

    @Test
    void crearProducto_ShouldReturn201() throws Exception {
        ProductoRequestDto request = ProductoRequestDto.builder()
                .nombre("Nuevo").categoria("Test").precio(BigDecimal.valueOf(100)).build();
        ProductoResponseDto response = ProductoResponseDto.builder()
                .id(1L).nombre("Nuevo").estadoProducto(EstadoProducto.ACTIVO).build();

        when(productoService.crearProducto(any(ProductoRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/productos")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nuevo"));
    }

    @Test
    void actualizarProducto_ShouldReturn200() throws Exception {
        ProductoRequestDto request = ProductoRequestDto.builder().nombre("Actualizado").build();
        ProductoResponseDto response = ProductoResponseDto.builder()
                .id(1L).nombre("Actualizado").build();

        when(productoService.actualizarProducto(eq(1L), any(ProductoRequestDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/productos/1")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Actualizado"));
    }

    @Test
    void borrarProducto_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/productos/1")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void actualizarProducto_WhenNotFound_Returns400() throws Exception {
        ProductoRequestDto request = ProductoRequestDto.builder().nombre("X").build();

        when(productoService.actualizarProducto(eq(99L), any(ProductoRequestDto.class)))
                .thenThrow(new ProductoNoEncontradoException("no encontrado"));

        mockMvc.perform(put("/api/productos/99")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
