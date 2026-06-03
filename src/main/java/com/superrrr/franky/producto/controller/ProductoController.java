package com.superrrr.franky.producto.controller;

import com.superrrr.franky.producto.dto.ProductoRequestDto;
import com.superrrr.franky.producto.dto.ProductoResponseDto;
import com.superrrr.franky.producto.service.ProductoService;
import com.superrrr.franky.producto.validation.CrearProductoGrupoValidacion;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ProductoResponseDto>> obtenerListaProductos(){
        List<ProductoResponseDto> productoResponseDtoList = productoService.obtenerProductos();
        return ResponseEntity.ok().body(productoResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDto> crearProducto(
            @Validated({Default.class, CrearProductoGrupoValidacion.class}) @RequestBody ProductoRequestDto productoRequestDto){
        ProductoResponseDto productoResponseDto = productoService.crearProducto(productoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> actualizarProducto(
            @PathVariable Long id,
            @Validated({Default.class}) @RequestBody ProductoRequestDto productoRequestDto){
        ProductoResponseDto productoResponseDto = productoService.actualizarProducto(id, productoRequestDto);
        return ResponseEntity.ok().body(productoResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long id){
        productoService.borrarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
