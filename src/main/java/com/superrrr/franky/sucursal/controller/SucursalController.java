package com.superrrr.franky.sucursal.controller;

import com.superrrr.franky.sucursal.dto.SucursalRequestDto;
import com.superrrr.franky.sucursal.dto.SucursalResponseDto;
import com.superrrr.franky.sucursal.service.SucursalService;
import com.superrrr.franky.sucursal.validation.CrearSucursalGrupoValidacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@Tag(name = "Sucursales", description = "Gestión de sucursales del supermercado")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Obtiene el listado completo de todas las sucursales registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Listado de sucursales obtenido exitosamente", content = @Content(schema = @Schema(implementation = SucursalResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<List<SucursalResponseDto>> obtenerListaSucursales() {
        List<SucursalResponseDto> sucursales = sucursalService.obtenerSucursales();
        return ResponseEntity.ok().body(sucursales);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Registrar sucursal", description = "Crea una nueva sucursal con nombre, dirección y teléfono. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente", content = @Content(schema = @Schema(implementation = SucursalResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "403", description = "No autorizado — se requiere rol ADMIN")
    public ResponseEntity<SucursalResponseDto> crearSucursal(
            @Validated({Default.class, CrearSucursalGrupoValidacion.class}) @RequestBody SucursalRequestDto sucursalRequestDto) {
        SucursalResponseDto sucursalResponseDto = sucursalService.crearSucursal(sucursalRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalResponseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Actualizar sucursal", description = "Modifica los datos de una sucursal existente por su ID. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente", content = @Content(schema = @Schema(implementation = SucursalResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "403", description = "No autorizado — se requiere rol ADMIN")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    public ResponseEntity<SucursalResponseDto> actualizarSucursal(
            @Parameter(description = "ID de la sucursal a actualizar") @PathVariable Long id,
            @Validated({Default.class}) @RequestBody SucursalRequestDto sucursalRequestDto) {
        SucursalResponseDto sucursalResponseDto = sucursalService.actualizarSucursal(id, sucursalRequestDto);
        return ResponseEntity.ok().body(sucursalResponseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Eliminar sucursal", description = "Elimina (borrado lógico) una sucursal del sistema por su ID. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "204", description = "Sucursal eliminada exitosamente")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "403", description = "No autorizado — se requiere rol ADMIN")
    public ResponseEntity<Void> borrarSucursal(
            @Parameter(description = "ID de la sucursal a eliminar") @PathVariable Long id) {
        sucursalService.borrarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
