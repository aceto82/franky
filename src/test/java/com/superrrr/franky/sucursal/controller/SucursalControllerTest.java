package com.superrrr.franky.sucursal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superrrr.franky.auth.TestJwtHelper;
import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.sucursal.dto.SucursalRequestDto;
import com.superrrr.franky.sucursal.dto.SucursalResponseDto;
import com.superrrr.franky.sucursal.enums.EstadoSucursal;
import com.superrrr.franky.sucursal.service.SucursalService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;

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
    void obtenerSucursales_ShouldReturn200() throws Exception {
        when(sucursalService.obtenerSucursales()).thenReturn(List.of(
                SucursalResponseDto.builder().id(1L).nombre("Centro").build()
        ));

        mockMvc.perform(get("/api/sucursales")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Centro"));
    }

    @Test
    void crearSucursal_ShouldReturn201() throws Exception {
        SucursalRequestDto request = SucursalRequestDto.builder()
                .nombre("Nueva").direccion("Av 1").telefono("555").build();
        SucursalResponseDto response = SucursalResponseDto.builder()
                .id(1L).nombre("Nueva").estadoSucursal(EstadoSucursal.ACTIVO).build();

        when(sucursalService.crearSucursal(any(SucursalRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/sucursales")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nueva"));
    }

    @Test
    void actualizarSucursal_ShouldReturn200() throws Exception {
        SucursalRequestDto request = SucursalRequestDto.builder().nombre("Actualizada").build();
        SucursalResponseDto response = SucursalResponseDto.builder()
                .id(1L).nombre("Actualizada").build();

        when(sucursalService.actualizarSucursal(eq(1L), any(SucursalRequestDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/sucursales/1")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Actualizada"));
    }

    @Test
    void borrarSucursal_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/sucursales/1")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }
}
