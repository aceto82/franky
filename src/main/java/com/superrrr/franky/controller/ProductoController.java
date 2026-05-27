package com.superrrr.franky.controller;

import com.superrrr.franky.dto.ProductoResponseDto;
import com.superrrr.franky.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> obtenerProductos(){
        List<ProductoResponseDto> productos = productoService.obtenerListaProductos();
        return ResponseEntity.ok().body(productos);
    }
}
