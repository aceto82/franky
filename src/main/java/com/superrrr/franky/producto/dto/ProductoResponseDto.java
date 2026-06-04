package com.superrrr.franky.producto.dto;

import com.superrrr.franky.producto.enums.EstadoProducto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos de respuesta de un producto")
public class ProductoResponseDto {

    @Schema(description = "ID del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Leche Entera 1L")
    private String nombre;

    @Schema(description = "Categoría del producto", example = "Lácteos")
    private String categoria;

    @Schema(description = "Precio del producto", example = "25.50")
    private BigDecimal precio;

    @Schema(description = "Estado del producto (ACTIVO, INACTIVO, ELIMINADO)", example = "ACTIVO")
    private EstadoProducto estadoProducto;

}
