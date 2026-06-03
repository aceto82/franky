package com.superrrr.franky.sucursal.controller;

import com.superrrr.franky.sucursal.dto.SucursalRequestDto;
import com.superrrr.franky.sucursal.dto.SucursalResponseDto;
import com.superrrr.franky.sucursal.service.SucursalService;
import com.superrrr.franky.sucursal.validation.CrearSucursalGrupoValidacion;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalResponseDto>> obtenerListaSucursales() {
        List<SucursalResponseDto> sucursales = sucursalService.obtenerSucursales();
        return ResponseEntity.ok().body(sucursales);
    }

    @PostMapping
    public ResponseEntity<SucursalResponseDto> crearSucursal(
            @Validated({Default.class, CrearSucursalGrupoValidacion.class}) @RequestBody SucursalRequestDto sucursalRequestDto) {
        SucursalResponseDto sucursalResponseDto = sucursalService.crearSucursal(sucursalRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponseDto> actualizarSucursal(
            @PathVariable Long id,
            @Validated({Default.class}) @RequestBody SucursalRequestDto sucursalRequestDto) {
        SucursalResponseDto sucursalResponseDto = sucursalService.actualizarSucursal(id, sucursalRequestDto);
        return ResponseEntity.ok().body(sucursalResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarSucursal(@PathVariable Long id) {
        sucursalService.borrarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
