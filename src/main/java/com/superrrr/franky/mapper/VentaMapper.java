package com.superrrr.franky.mapper;

import com.superrrr.franky.dto.VentaResponseDto;
import com.superrrr.franky.model.Venta;

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
