package com.superrrr.franky.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaFiltrosDto {

    @NotNull(message = "La sucursal es obligatoria")
    @Positive(message = "La sucursal debe ser mayor que cero")
    private Long sucursalId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
}
