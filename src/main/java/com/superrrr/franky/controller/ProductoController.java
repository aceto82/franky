package com.superrrr.franky.controller;

import com.superrrr.franky.dto.ProductoRequestDto;
import com.superrrr.franky.dto.ProductoResponseDto;
import com.superrrr.franky.dto.validators.CrearProductoGrupoValidacion;
import com.superrrr.franky.service.ProductoService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que realiza las diferentes acciones relacionada al manejo de los productos
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Listar todos los productos registrados.
     *
     * @return Obtener listado de producto
     */
    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> obtenerProductos() {
        List<ProductoResponseDto> productos = productoService.obtenerListaProductos();
        return ResponseEntity.ok().body(productos);
    }

    /**
     * Crear un nuevo producto con nombre, precio y categoría.
     *
     * @param productoRequestDto DTO de entrada con la información del producto a crear
     * @return Nuevo producto
     */
    @PostMapping
    public ResponseEntity<ProductoResponseDto> crearProducto(
            @Validated({Default.class, CrearProductoGrupoValidacion.class}) @RequestBody ProductoRequestDto productoRequestDto) {
        ProductoResponseDto productoResponseDto = productoService.crearProducto(productoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoResponseDto);
    }

    /**
     * Modificar los datos de un producto específico.
     *
     * @param id                 Identificador del producto
     * @param productoRequestDto Datos del producto a actualizar
     * @return Producto actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> actualizarProducto(
            @PathVariable Long id,
            @Validated({Default.class}) @RequestBody ProductoRequestDto productoRequestDto) {
        ProductoResponseDto productoResponseDto = productoService.actualizarProducto(id, productoRequestDto);

        return ResponseEntity.ok().body(productoResponseDto);
    }

    /**
     * Eliminar un producto del sistema.
     *
     * @param id Identificador del producto
     * @return Producto eliminado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
