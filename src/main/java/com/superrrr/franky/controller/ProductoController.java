package com.superrrr.franky.controller;

import com.superrrr.franky.dto.ProductoRequestDto;
import com.superrrr.franky.dto.ProductoResponseDto;
import com.superrrr.franky.service.ProductoService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> obtenerProductos() {
        List<ProductoResponseDto> productos = productoService.obtenerListaProductos();
        return ResponseEntity.ok().body(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDto> crearProducto(@Validated({Default.class}) @RequestBody ProductoRequestDto productoRequestDto) {
        ProductoResponseDto productoResponseDto = productoService.crearProducto(productoRequestDto);
        return ResponseEntity.ok().body(productoResponseDto);
    }
}
