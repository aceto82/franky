package com.superrrr.franky.sucursal.dto;

import com.superrrr.franky.sucursal.validation.CrearSucursalGrupoValidacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una sucursal")
public class SucursalRequestDto {

    @NotBlank(groups = CrearSucursalGrupoValidacion.class, message = "Nombre es requerido")
    @Schema(description = "Nombre de la sucursal", example = "Sucursal Centro")
    private String nombre;

    @NotBlank(groups = CrearSucursalGrupoValidacion.class, message = "Direccion es requerido")
    @Schema(description = "Dirección de la sucursal", example = "Av. Principal 123")
    private String direccion;

    @Schema(description = "Teléfono de contacto", example = "555-0123")
    private String telefono;

}
