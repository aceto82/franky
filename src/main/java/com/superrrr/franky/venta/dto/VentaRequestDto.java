package com.superrrr.franky.venta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(description = "Datos para registrar una nueva venta")
public class VentaRequestDto {

    @NotNull(message = "Se requiere el ID de la sucursal")
    @Schema(description = "ID de la sucursal donde se realiza la venta", example = "1")
    private Long sucursalId;

    @NotEmpty(message = "Debe existir al menos un producto en la lista del detalle de la venta")
    @Valid
    @Schema(description = "Lista de productos con cantidades")
    private List<DetalleVentaRequestDto> detalle;
}
