package com.superrrr.franky.controller;

import com.superrrr.franky.dto.VentaRequestDto;
import com.superrrr.franky.dto.VentaResponseDto;
import com.superrrr.franky.service.VentaService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDto> crearVenta(
            @Validated({Default.class}) @RequestBody VentaRequestDto ventaRequestDto){
        VentaResponseDto ventaResponseDto = ventaService.CrearVenta(ventaRequestDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(ventaResponseDto);
    }
}
