package com.superrrr.franky.producto.controller;

import com.superrrr.franky.producto.dto.ProductoRequestDto;
import com.superrrr.franky.producto.dto.ProductoResponseDto;
import com.superrrr.franky.producto.service.ProductoService;
import com.superrrr.franky.producto.validation.CrearProductoGrupoValidacion;
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
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestión de productos del supermercado")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene el listado completo de todos los productos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Listado de productos obtenido exitosamente", content = @Content(schema = @Schema(implementation = ProductoResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<List<ProductoResponseDto>> obtenerListaProductos(){
        List<ProductoResponseDto> productoResponseDtoList = productoService.obtenerProductos();
        return ResponseEntity.ok().body(productoResponseDtoList);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Registrar producto", description = "Crea un nuevo producto con nombre, precio y categoría. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente", content = @Content(schema = @Schema(implementation = ProductoResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "403", description = "No autorizado — se requiere rol ADMIN")
    public ResponseEntity<ProductoResponseDto> crearProducto(
            @Validated({Default.class, CrearProductoGrupoValidacion.class}) @RequestBody ProductoRequestDto productoRequestDto){
        ProductoResponseDto productoResponseDto = productoService.crearProducto(productoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoResponseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto específico por su ID. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente", content = @Content(schema = @Schema(implementation = ProductoResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "403", description = "No autorizado — se requiere rol ADMIN")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<ProductoResponseDto> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar") @PathVariable Long id,
            @Validated({Default.class}) @RequestBody ProductoRequestDto productoRequestDto){
        ProductoResponseDto productoResponseDto = productoService.actualizarProducto(id, productoRequestDto);
        return ResponseEntity.ok().body(productoResponseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Eliminar producto", description = "Elimina (borrado lógico) un producto del sistema por su ID. Requiere rol ADMIN.")
    @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "403", description = "No autorizado — se requiere rol ADMIN")
    public ResponseEntity<Void> borrarProducto(
            @Parameter(description = "ID del producto a eliminar") @PathVariable Long id){
        productoService.borrarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
