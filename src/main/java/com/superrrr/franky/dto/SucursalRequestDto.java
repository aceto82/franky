package com.superrrr.franky.dto;

import com.superrrr.franky.dto.validators.CrearSucursalGrupoValidacion;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequestDto {

    @NotBlank(groups = CrearSucursalGrupoValidacion.class, message = "Nombre es requerido")
    private String nombre;

    @NotBlank(groups = CrearSucursalGrupoValidacion.class, message = "Direccion es requerido")
    private String direccion;

    private String telefono;

}
