package com.superrrr.franky.estadistica.controller;

import com.superrrr.franky.estadistica.dto.ProductoMasVendidoDto;
import com.superrrr.franky.estadistica.service.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaController {

    @Autowired
    private EstadisticaService estadisticaService;

    @GetMapping("/producto-mas-vendido")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductoMasVendidoDto> obtenerProductoMasVendido() {
        ProductoMasVendidoDto dto = estadisticaService.obtenerProductoMasVendido();
        return ResponseEntity.ok(dto);
    }
}
