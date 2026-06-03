package com.superrrr.franky.producto.dto;

import com.superrrr.franky.producto.enums.EstadoProducto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponseDto {

    private Long id;

    private String nombre;

    private String categoria;

    private BigDecimal precio;

    private EstadoProducto estadoProducto;

}
