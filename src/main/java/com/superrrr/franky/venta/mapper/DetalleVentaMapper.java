package com.superrrr.franky.venta.mapper;

import com.superrrr.franky.venta.dto.DetalleVentaResponseDto;
import com.superrrr.franky.venta.entity.DetalleVenta;

public class DetalleVentaMapper {

    public static DetalleVentaResponseDto toDTO(DetalleVenta detalleVenta){
        return DetalleVentaResponseDto.builder()
                .id(detalleVenta.getId())
                .cantidad(detalleVenta.getCantidad())
                .precioUnitario(detalleVenta.getPrecioUnitario())
                .subtotal(detalleVenta.getSubtotal())
                .productoId(detalleVenta.getProducto().getId())
                .ventaId(detalleVenta.getVenta().getId())
                .build();
    }
}
