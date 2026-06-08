package com.superrrr.franky.venta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superrrr.franky.auth.TestJwtHelper;
import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.venta.dto.DetalleVentaRequestDto;
import com.superrrr.franky.venta.dto.VentaRequestDto;
import com.superrrr.franky.venta.dto.VentaResponseDto;
import com.superrrr.franky.venta.enums.EstadoVenta;
import com.superrrr.franky.venta.exception.IdempotencyKeyRequeridaException;
import com.superrrr.franky.venta.service.VentaService;
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

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VentaService ventaService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String userToken;
    private String adminToken;

    @BeforeEach
    void setUp() {
        userToken = TestJwtHelper.generateToken(
                Usuario.builder().username("user").rol(Rol.USER).estadoUsuario(EstadoUsuario.ACTIVO).build()
        );
        adminToken = TestJwtHelper.generateToken(
                Usuario.builder().username("admin").rol(Rol.ADMIN).estadoUsuario(EstadoUsuario.ACTIVO).build()
        );
        when(userDetailsService.loadUserByUsername("user"))
                .thenReturn(new User("user", "", List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        when(userDetailsService.loadUserByUsername("admin"))
                .thenReturn(new User("admin", "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        lenient().when(ventaService.existePorIdempotencyKey(any())).thenReturn(false);
    }

    @Test
    void crearVenta_ShouldReturn201() throws Exception {
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(1L, 2)));

        VentaResponseDto response = VentaResponseDto.builder()
                .id(1L).fecha(Instant.now()).estadoVenta(EstadoVenta.ACTIVO).build();

        when(ventaService.existePorIdempotencyKey(any())).thenReturn(false);
        when(ventaService.CrearVenta(any(VentaRequestDto.class), any())).thenReturn(response);

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + userToken)
                        .header("Idempotency-Key", "key-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crearVenta_WithIdempotencyKey_ShouldReturn201() throws Exception {
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(1L, 2)));

        VentaResponseDto response = VentaResponseDto.builder()
                .id(1L).fecha(Instant.now()).estadoVenta(EstadoVenta.ACTIVO).build();

        when(ventaService.CrearVenta(any(VentaRequestDto.class), eq("key-123"))).thenReturn(response);

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + userToken)
                        .header("Idempotency-Key", "key-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crearVenta_WithIdempotencyKey_ShouldReturn200OnRetry() throws Exception {
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(1L, 2)));

        VentaResponseDto response = VentaResponseDto.builder()
                .id(1L).fecha(Instant.now()).estadoVenta(EstadoVenta.ACTIVO).build();

        when(ventaService.existePorIdempotencyKey("key-123")).thenReturn(true);
        when(ventaService.CrearVenta(any(VentaRequestDto.class), eq("key-123"))).thenReturn(response);

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + userToken)
                        .header("Idempotency-Key", "key-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crearVenta_WithoutIdempotencyKey_ShouldReturn400() throws Exception {
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(1L, 2)));

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearVenta_WithBlankIdempotencyKey_ShouldReturn400() throws Exception {
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(1L, 2)));

        when(ventaService.CrearVenta(any(VentaRequestDto.class), eq("")))
                .thenThrow(new IdempotencyKeyRequeridaException("El header Idempotency-Key es requerido para crear una venta"));

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + userToken)
                        .header("Idempotency-Key", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerVentas_ShouldReturn200() throws Exception {
        when(ventaService.obtenerVentasPorSucursalYFecha(anyLong(), any()))
                .thenReturn(List.of(
                        VentaResponseDto.builder().id(1L).build()
                ));

        mockMvc.perform(get("/api/ventas")
                        .header("Authorization", "Bearer " + userToken)
                        .param("sucursalId", "1")
                        .param("fecha", "2026-06-04"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void borrarVenta_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/ventas/1")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }
}
