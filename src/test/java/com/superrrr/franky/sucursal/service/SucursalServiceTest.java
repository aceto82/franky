package com.superrrr.franky.sucursal.service;

import com.superrrr.franky.sucursal.dto.SucursalRequestDto;
import com.superrrr.franky.sucursal.dto.SucursalResponseDto;
import com.superrrr.franky.sucursal.entity.Sucursal;
import com.superrrr.franky.sucursal.enums.EstadoSucursal;
import com.superrrr.franky.sucursal.exception.SucursalNoEncontradoException;
import com.superrrr.franky.sucursal.repositories.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @Test
    void obtenerSucursales_ShouldReturnNonEliminadas() {
        Sucursal s1 = Sucursal.builder().id(1L).nombre("S1").direccion("D1").estadoSucursal(EstadoSucursal.ACTIVO).build();
        Sucursal s2 = Sucursal.builder().id(2L).nombre("S2").direccion("D2").estadoSucursal(EstadoSucursal.INACTIVO).build();

        when(sucursalRepository.findByEstadoSucursalNot(EstadoSucursal.ELIMINADO))
                .thenReturn(List.of(s1, s2));

        List<SucursalResponseDto> result = sucursalService.obtenerSucursales();

        assertEquals(2, result.size());
    }

    @Test
    void crearSucursal_ShouldSaveWithActivoEstado() {
        SucursalRequestDto request = SucursalRequestDto.builder()
                .nombre("Nueva Sucursal").direccion("Av. Siempre Viva").telefono("555-0000").build();
        Sucursal saved = Sucursal.builder()
                .id(1L).nombre("Nueva Sucursal").direccion("Av. Siempre Viva")
                .telefono("555-0000").estadoSucursal(EstadoSucursal.ACTIVO).build();

        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(saved);

        SucursalResponseDto result = sucursalService.crearSucursal(request);

        ArgumentCaptor<Sucursal> captor = ArgumentCaptor.forClass(Sucursal.class);
        verify(sucursalRepository).save(captor.capture());
        assertEquals(EstadoSucursal.ACTIVO, captor.getValue().getEstadoSucursal());
        assertEquals("Nueva Sucursal", result.getNombre());
    }

    @Test
    void actualizarSucursal_ShouldUpdateOnlyProvidedFields() {
        Sucursal existing = Sucursal.builder()
                .id(1L).nombre("Original").direccion("Dir Original")
                .telefono("111").estadoSucursal(EstadoSucursal.ACTIVO).build();
        SucursalRequestDto request = SucursalRequestDto.builder()
                .nombre("Actualizado").build();

        when(sucursalRepository.findByIdAndEstadoSucursalNot(1L, EstadoSucursal.ELIMINADO))
                .thenReturn(Optional.of(existing));
        when(sucursalRepository.save(any(Sucursal.class))).thenAnswer(i -> i.getArgument(0));

        SucursalResponseDto result = sucursalService.actualizarSucursal(1L, request);

        assertEquals("Actualizado", result.getNombre());
        assertEquals("Dir Original", result.getDireccion());
    }

    @Test
    void actualizarSucursal_ShouldThrowWhenNotFound() {
        when(sucursalRepository.findByIdAndEstadoSucursalNot(99L, EstadoSucursal.ELIMINADO))
                .thenReturn(Optional.empty());

        SucursalRequestDto request = SucursalRequestDto.builder().nombre("X").build();

        assertThrows(SucursalNoEncontradoException.class,
                () -> sucursalService.actualizarSucursal(99L, request));
    }

    @Test
    void borrarSucursal_ShouldSetEliminado() {
        Sucursal existing = Sucursal.builder()
                .id(1L).nombre("S").direccion("D")
                .estadoSucursal(EstadoSucursal.ACTIVO).build();

        when(sucursalRepository.findByIdAndEstadoSucursalNot(1L, EstadoSucursal.ELIMINADO))
                .thenReturn(Optional.of(existing));

        sucursalService.borrarSucursal(1L);

        assertEquals(EstadoSucursal.ELIMINADO, existing.getEstadoSucursal());
        verify(sucursalRepository).save(existing);
    }
}
