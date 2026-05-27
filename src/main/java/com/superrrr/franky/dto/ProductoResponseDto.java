package com.superrrr.franky.dto;

import com.superrrr.franky.enums.EstadoProducto;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDto {

    private Long id;

    private String nombre;

    private String categoria;

    private BigDecimal precio;

    private EstadoProducto estadoProducto;
}
