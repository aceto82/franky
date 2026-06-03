package com.superrrr.franky.venta.mapper;

import com.superrrr.franky.venta.dto.VentaResponseDto;
import com.superrrr.franky.venta.entity.Venta;

public class VentaMapper {

    public static VentaResponseDto toDTO(Venta venta){
        return VentaResponseDto.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .sucursalId(venta.getSucursal().getId())
                .estadoVenta(venta.getEstadoVenta())
                .detalleVentaResponseDtoList(
                        venta.getDetalles().stream().map(DetalleVentaMapper::toDTO).toList()
                ).build();
    }
}
