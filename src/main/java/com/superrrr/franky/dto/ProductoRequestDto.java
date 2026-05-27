package com.superrrr.franky.dto;

import com.superrrr.franky.enums.EstadoProducto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDto {

    @NotBlank(message = "Nombre es requerido")
    private String nombre;

    private String categoria;

    @NotBlank(message = "Precio del producto es requerido")
    private BigDecimal precio;
}
