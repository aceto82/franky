package com.superrrr.franky.venta.controller;

import com.superrrr.franky.venta.dto.VentaFiltrosDto;
import com.superrrr.franky.venta.dto.VentaRequestDto;
import com.superrrr.franky.venta.dto.VentaResponseDto;
import com.superrrr.franky.venta.service.VentaService;
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
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<VentaResponseDto> crearVenta(
            @Validated({Default.class}) @RequestBody VentaRequestDto ventaRequestDto) {
        VentaResponseDto ventaResponseDto = ventaService.CrearVenta(ventaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDto>> obtenerVentasPorSucursalYFecha(
            @Validated({Default.class}) VentaFiltrosDto ventaFiltrosDto
    ) {
        List<VentaResponseDto> ventaResponseDtoList = ventaService.obtenerVentasPorSucursalYFecha(ventaFiltrosDto.getSucursalId(), ventaFiltrosDto.getFecha());
        return ResponseEntity.ok().body(ventaResponseDtoList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public  ResponseEntity<Void> borrarVenta(@PathVariable Long id) {
        ventaService.borrarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
