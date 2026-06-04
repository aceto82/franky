package com.superrrr.franky.sucursal.mapper;

import com.superrrr.franky.sucursal.dto.SucursalRequestDto;
import com.superrrr.franky.sucursal.dto.SucursalResponseDto;
import com.superrrr.franky.sucursal.entity.Sucursal;
import com.superrrr.franky.sucursal.enums.EstadoSucursal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SucursalMapperTest {

    @Test
    void toDTO_ShouldMapAllFields() {
        Sucursal sucursal = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Centro")
                .direccion("Av. Principal 123")
                .telefono("555-1234")
                .estadoSucursal(EstadoSucursal.ACTIVO)
                .build();

        SucursalResponseDto dto = SucursalMapper.toDTO(sucursal);

        assertEquals(1L, dto.getId());
        assertEquals("Sucursal Centro", dto.getNombre());
        assertEquals("Av. Principal 123", dto.getDireccion());
        assertEquals("555-1234", dto.getTelefono());
        assertEquals(EstadoSucursal.ACTIVO, dto.getEstadoSucursal());
    }

    @Test
    void toModel_ShouldMapAllFields() {
        SucursalRequestDto requestDto = SucursalRequestDto.builder()
                .nombre("Sucursal Norte")
                .direccion("Calle Secundaria 456")
                .telefono("555-5678")
                .build();

        Sucursal sucursal = SucursalMapper.toModel(requestDto);

        assertNull(sucursal.getId());
        assertEquals("Sucursal Norte", sucursal.getNombre());
        assertEquals("Calle Secundaria 456", sucursal.getDireccion());
        assertEquals("555-5678", sucursal.getTelefono());
        assertNull(sucursal.getEstadoSucursal());
    }

    @Test
    void toModel_ShouldHandleNullFields() {
        SucursalRequestDto requestDto = SucursalRequestDto.builder().build();

        Sucursal sucursal = SucursalMapper.toModel(requestDto);

        assertNull(sucursal.getNombre());
        assertNull(sucursal.getDireccion());
        assertNull(sucursal.getTelefono());
    }
}
