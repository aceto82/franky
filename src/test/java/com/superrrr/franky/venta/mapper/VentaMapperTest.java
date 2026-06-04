package com.superrrr.franky.venta.mapper;

import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import com.superrrr.franky.sucursal.entity.Sucursal;
import com.superrrr.franky.sucursal.enums.EstadoSucursal;
import com.superrrr.franky.venta.dto.VentaResponseDto;
import com.superrrr.franky.venta.entity.DetalleVenta;
import com.superrrr.franky.venta.entity.Venta;
import com.superrrr.franky.venta.enums.EstadoVenta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VentaMapperTest {

    @Test
    void toDTO_ShouldMapVentaWithDetalles() {
        Sucursal sucursal = Sucursal.builder().id(10L).build();
        Producto producto = Producto.builder()
                .id(100L)
                .nombre("Coca Cola")
                .precio(BigDecimal.valueOf(2.5))
                .build();

        Venta venta = Venta.builder()
                .id(1L)
                .fecha(Instant.parse("2026-06-04T10:00:00Z"))
                .sucursal(sucursal)
                .estadoVenta(EstadoVenta.ACTIVO)
                .build();

        DetalleVenta detalle = DetalleVenta.builder()
                .id(1L)
                .cantidad(3)
                .precioUnitario(BigDecimal.valueOf(2.5))
                .subtotal(BigDecimal.valueOf(7.5))
                .producto(producto)
                .venta(venta)
                .build();

        venta.setDetalles(List.of(detalle));

        VentaResponseDto dto = VentaMapper.toDTO(venta);

        assertEquals(1L, dto.getId());
        assertEquals(Instant.parse("2026-06-04T10:00:00Z"), dto.getFecha());
        assertEquals(10L, dto.getSucursalId());
        assertEquals(EstadoVenta.ACTIVO, dto.getEstadoVenta());
        assertNotNull(dto.getDetalleVentaResponseDtoList());
        assertEquals(1, dto.getDetalleVentaResponseDtoList().size());
        assertEquals(100L, dto.getDetalleVentaResponseDtoList().getFirst().getProductoId());
        assertEquals(3, dto.getDetalleVentaResponseDtoList().getFirst().getCantidad());
    }

    @Test
    void toDTO_ShouldHandleEmptyDetalles() {
        Sucursal sucursal = Sucursal.builder().id(10L).build();

        Venta venta = Venta.builder()
                .id(2L)
                .fecha(Instant.parse("2026-06-04T10:00:00Z"))
                .sucursal(sucursal)
                .estadoVenta(EstadoVenta.ACTIVO)
                .detalles(List.of())
                .build();

        VentaResponseDto dto = VentaMapper.toDTO(venta);

        assertNotNull(dto.getDetalleVentaResponseDtoList());
        assertTrue(dto.getDetalleVentaResponseDtoList().isEmpty());
    }
}
