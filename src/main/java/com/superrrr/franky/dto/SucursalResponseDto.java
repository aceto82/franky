package com.superrrr.franky.dto;

import com.superrrr.franky.enums.EstadoSucursal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponseDto {

    private Long id;

    private String nombre;

    private String direccion;

    private String telefono;

    private EstadoSucursal estadoSucursal;

}
