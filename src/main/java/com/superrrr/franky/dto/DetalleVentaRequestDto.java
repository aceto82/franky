package com.superrrr.franky.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaRequestDto {

    @NotNull(message = "Se requiere el ID del producto")
    private Long productoId;

    @NotNull(message = "Se requiere la cantidad de productos")
    private int cantidad;
}
