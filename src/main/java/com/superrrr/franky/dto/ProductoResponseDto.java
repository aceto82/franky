package com.superrrr.franky.dto;

import com.superrrr.franky.enums.EstadoProducto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
public class ProductoResponseDto {

    private Long id;

    private String nombre;

    private String categoria;

    private BigDecimal precio;

    private EstadoProducto estadoProducto;
}
