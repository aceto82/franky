package com.superrrr.franky.producto.dto;

import com.superrrr.franky.producto.validation.CrearProductoGrupoValidacion;
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
public class ProductoRequestDto {

    @NotBlank(groups = CrearProductoGrupoValidacion.class, message = "Nombre es requerido")
    private String nombre;

    private String categoria;

    @NotNull(groups = CrearProductoGrupoValidacion.class, message = "Precio es requerido")
    @Positive(groups = CrearProductoGrupoValidacion.class, message = "El precio debe ser positivo")
    private BigDecimal precio;

}
