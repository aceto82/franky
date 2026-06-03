package com.superrrr.franky.venta.dto;

import com.superrrr.franky.venta.enums.EstadoVenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDto {

    private Long id;

    private Instant fecha;

    private Long sucursalId;

    private EstadoVenta estadoVenta;

    private List<DetalleVentaResponseDto> detalleVentaResponseDtoList;
}
