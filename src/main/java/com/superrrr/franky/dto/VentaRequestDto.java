package com.superrrr.franky.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDto {

    @NotNull(message = "Se requiere el ID de la sucursal")
    private Long sucursalId;

    @NotEmpty(message = "Debe existir al menos un producto en la lista del detalle de la venta")
    private List<DetalleVentaRequestDto> detalle;
}
