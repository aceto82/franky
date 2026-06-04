package com.superrrr.franky.venta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle de un producto en una venta")
public class DetalleVentaRequestDto {

    @NotNull(message = "Se requiere el ID del producto")
    @Schema(description = "ID del producto", example = "1")
    private Long productoId;

    @NotNull(message = "Se requiere la cantidad de productos")
    @Schema(description = "Cantidad del producto", example = "2")
    private Integer cantidad;
}
