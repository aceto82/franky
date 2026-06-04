package com.superrrr.franky.producto.dto;

import com.superrrr.franky.producto.validation.CrearProductoGrupoValidacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos para crear o actualizar un producto")
public class ProductoRequestDto {

    @NotBlank(groups = CrearProductoGrupoValidacion.class, message = "Nombre es requerido")
    @Schema(description = "Nombre del producto", example = "Leche Entera 1L")
    private String nombre;

    @Schema(description = "Categoría del producto", example = "Lácteos")
    private String categoria;

    @NotNull(groups = CrearProductoGrupoValidacion.class, message = "Precio es requerido")
    @Positive(groups = CrearProductoGrupoValidacion.class, message = "El precio debe ser positivo")
    @Schema(description = "Precio del producto", example = "25.50")
    private BigDecimal precio;

}
