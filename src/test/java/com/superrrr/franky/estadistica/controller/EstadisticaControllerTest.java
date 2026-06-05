package com.superrrr.franky.estadistica.controller;

import com.superrrr.franky.auth.TestJwtHelper;
import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.estadistica.dto.ProductoMasVendidoDto;
import com.superrrr.franky.estadistica.service.EstadisticaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class EstadisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EstadisticaService estadisticaService;

    @MockitoBean
    private UserDetailsService userDetailsService;

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
    void obtenerProductoMasVendido_ShouldReturn200() throws Exception {
        ProductoMasVendidoDto dto = ProductoMasVendidoDto.builder()
                .id(1L).nombre("Producto Top").totalVendido(100L).build();

        when(estadisticaService.obtenerProductoMasVendido()).thenReturn(dto);

        mockMvc.perform(get("/api/estadisticas/producto-mas-vendido")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto Top"))
                .andExpect(jsonPath("$.totalVendido").value(100));
    }
}
