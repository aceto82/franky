package com.superrrr.franky.producto.mapper;

import com.superrrr.franky.producto.dto.ProductoRequestDto;
import com.superrrr.franky.producto.dto.ProductoResponseDto;
import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductoMapperTest {

    @Test
    void toDTO_ShouldMapAllFields() {
        Producto producto = Producto.builder()
                .id(1L)
                .nombre("Test Product")
                .categoria("Bebidas")
                .precio(BigDecimal.valueOf(10.5))
                .estadoProducto(EstadoProducto.ACTIVO)
                .build();

        ProductoResponseDto dto = ProductoMapper.toDTO(producto);

        assertEquals(1L, dto.getId());
        assertEquals("Test Product", dto.getNombre());
        assertEquals("Bebidas", dto.getCategoria());
        assertEquals(BigDecimal.valueOf(10.5), dto.getPrecio());
        assertEquals(EstadoProducto.ACTIVO, dto.getEstadoProducto());
    }

    @Test
    void toModel_ShouldMapAllFields() {
        ProductoRequestDto requestDto = ProductoRequestDto.builder()
                .nombre("New Product")
                .categoria("Lacteos")
                .precio(BigDecimal.valueOf(25.0))
                .build();

        Producto producto = ProductoMapper.toModel(requestDto);

        assertNull(producto.getId());
        assertEquals("New Product", producto.getNombre());
        assertEquals("Lacteos", producto.getCategoria());
        assertEquals(BigDecimal.valueOf(25.0), producto.getPrecio());
        assertNull(producto.getEstadoProducto());
    }

    @Test
    void toModel_ShouldHandleNullFields() {
        ProductoRequestDto requestDto = ProductoRequestDto.builder().build();

        Producto producto = ProductoMapper.toModel(requestDto);

        assertNull(producto.getNombre());
        assertNull(producto.getCategoria());
        assertNull(producto.getPrecio());
    }
}
