package com.superrrr.franky.venta.mapper;

import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import com.superrrr.franky.venta.dto.DetalleVentaResponseDto;
import com.superrrr.franky.venta.entity.DetalleVenta;
import com.superrrr.franky.venta.entity.Venta;
import com.superrrr.franky.venta.enums.EstadoVenta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DetalleVentaMapperTest {

    @Test
    void toDTO_ShouldMapAllFields() {
        Producto producto = Producto.builder().id(100L).build();
        Venta venta = Venta.builder().id(1L).build();

        DetalleVenta detalle = DetalleVenta.builder()
                .id(5L)
                .cantidad(2)
                .precioUnitario(BigDecimal.valueOf(15.0))
                .subtotal(BigDecimal.valueOf(30.0))
                .producto(producto)
                .venta(venta)
                .build();

        DetalleVentaResponseDto dto = DetalleVentaMapper.toDTO(detalle);

        assertEquals(5L, dto.getId());
        assertEquals(2, dto.getCantidad());
        assertEquals(BigDecimal.valueOf(15.0), dto.getPrecioUnitario());
        assertEquals(BigDecimal.valueOf(30.0), dto.getSubtotal());
        assertEquals(100L, dto.getProductoId());
        assertEquals(1L, dto.getVentaId());
    }
}
