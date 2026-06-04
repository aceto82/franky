package com.superrrr.franky.estadistica.controller;

import com.superrrr.franky.estadistica.dto.ProductoMasVendidoDto;
import com.superrrr.franky.estadistica.service.EstadisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
@Tag(name = "Estadísticas", description = "Endpoints de estadísticas y reportes")
public class EstadisticaController {

    @Autowired
    private EstadisticaService estadisticaService;

    @GetMapping("/producto-mas-vendido")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Producto más vendido", description = "Calcula y devuelve el producto más vendido del supermercado según la cantidad total vendida")
    @ApiResponse(responseCode = "200", description = "Producto más vendido obtenido exitosamente", content = @Content(schema = @Schema(implementation = ProductoMasVendidoDto.class)))
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<ProductoMasVendidoDto> obtenerProductoMasVendido() {
        ProductoMasVendidoDto dto = estadisticaService.obtenerProductoMasVendido();
        return ResponseEntity.ok(dto);
    }
}
