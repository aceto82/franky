package com.superrrr.franky.venta.controller;

import com.superrrr.franky.venta.dto.VentaFiltrosDto;
import com.superrrr.franky.venta.dto.VentaRequestDto;
import com.superrrr.franky.venta.dto.VentaResponseDto;
import com.superrrr.franky.venta.service.VentaService;
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
@RequestMapping("/api/ventas")
@Tag(name = "Ventas", description = "Gestión de ventas del supermercado")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Registrar venta", description = "Crea una nueva venta para una sucursal con productos y cantidades. Soporta idempotencia mediante el header Idempotency-Key. Requiere rol ADMIN o USER.")
    @ApiResponse(responseCode = "200", description = "Venta existente devuelta por idempotencia (misma Idempotency-Key usada anteriormente)", content = @Content(schema = @Schema(implementation = VentaResponseDto.class)))
    @ApiResponse(responseCode = "201", description = "Venta creada exitosamente", content = @Content(schema = @Schema(implementation = VentaResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    public ResponseEntity<VentaResponseDto> crearVenta(
            @RequestHeader(value = "Idempotency-Key", required = false)
            @Parameter(description = "Clave de idempotencia para evitar duplicados (UUID recomendado)")
            String idempotencyKey,
            @Validated({Default.class}) @RequestBody VentaRequestDto ventaRequestDto) {
        boolean isRetry = false;
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            isRetry = ventaService.existePorIdempotencyKey(idempotencyKey);
        }
        VentaResponseDto ventaResponseDto = ventaService.CrearVenta(ventaRequestDto, idempotencyKey);
        if (isRetry) {
            return ResponseEntity.ok(ventaResponseDto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaResponseDto);
    }

    @GetMapping
    @Operation(summary = "Obtener ventas por sucursal y fecha", description = "Lista las ventas realizadas en una fecha específica para una sucursal determinada")
    @ApiResponse(responseCode = "200", description = "Listado de ventas obtenido exitosamente", content = @Content(schema = @Schema(implementation = VentaResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Parámetros de filtro inválidos")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    public ResponseEntity<List<VentaResponseDto>> obtenerVentasPorSucursalYFecha(
            @Parameter(description = "Filtros: ID de sucursal y fecha (formato yyyy-MM-dd)") @Validated({Default.class}) VentaFiltrosDto ventaFiltrosDto
    ) {
        List<VentaResponseDto> ventaResponseDtoList = ventaService.obtenerVentasPorSucursalYFecha(ventaFiltrosDto.getSucursalId(), ventaFiltrosDto.getFecha());
        return ResponseEntity.ok().body(ventaResponseDtoList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Anular venta", description = "Elimina (borrado lógico) una venta registrada por su ID. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "204", description = "Venta anulada exitosamente")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "403", description = "No autorizado — se requiere rol ADMIN")
    public  ResponseEntity<Void> borrarVenta(
            @Parameter(description = "ID de la venta a anular") @PathVariable Long id) {
        ventaService.borrarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
