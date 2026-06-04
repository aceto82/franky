package com.superrrr.franky.estadistica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoMasVendidoDto {

    private Long id;
    private String nombre;
    private String categoria;
    private BigDecimal precio;
    private Long totalVendido;
}
