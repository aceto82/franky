package com.superrrr.franky.producto.service;

import com.superrrr.franky.producto.dto.ProductoRequestDto;
import com.superrrr.franky.producto.dto.ProductoResponseDto;
import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import com.superrrr.franky.producto.exception.ProductoNoEncontradoException;
import com.superrrr.franky.producto.repositories.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void obtenerProductos_ShouldReturnNonEliminados() {
        Producto p1 = Producto.builder().id(1L).nombre("P1").precio(BigDecimal.TEN).estadoProducto(EstadoProducto.ACTIVO).build();
        Producto p2 = Producto.builder().id(2L).nombre("P2").precio(BigDecimal.ONE).estadoProducto(EstadoProducto.INACTIVO).build();

        when(productoRepository.findByEstadoProductoNot(EstadoProducto.ELIMINADO))
                .thenReturn(List.of(p1, p2));

        List<ProductoResponseDto> result = productoService.obtenerProductos();

        assertEquals(2, result.size());
        assertEquals("P1", result.get(0).getNombre());
        assertEquals("P2", result.get(1).getNombre());
    }

    @Test
    void crearProducto_ShouldSaveWithActivoEstado() {
        ProductoRequestDto request = ProductoRequestDto.builder()
                .nombre("Nuevo")
                .categoria("Test")
                .precio(BigDecimal.valueOf(100))
                .build();
        Producto saved = Producto.builder()
                .id(1L).nombre("Nuevo").categoria("Test")
                .precio(BigDecimal.valueOf(100))
                .estadoProducto(EstadoProducto.ACTIVO)
                .build();

        when(productoRepository.save(any(Producto.class))).thenReturn(saved);

        ProductoResponseDto result = productoService.crearProducto(request);

        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).save(captor.capture());
        assertEquals(EstadoProducto.ACTIVO, captor.getValue().getEstadoProducto());
        assertEquals("Nuevo", result.getNombre());
    }

    @Test
    void actualizarProducto_ShouldUpdateOnlyProvidedFields() {
        Producto existing = Producto.builder()
                .id(1L).nombre("Original").categoria("OriginalCat")
                .precio(BigDecimal.valueOf(50)).estadoProducto(EstadoProducto.ACTIVO)
                .build();
        ProductoRequestDto request = ProductoRequestDto.builder()
                .nombre("Actualizado")
                .build();

        when(productoRepository.findByIdAndEstadoProductoNot(1L, EstadoProducto.ELIMINADO))
                .thenReturn(Optional.of(existing));
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        ProductoResponseDto result = productoService.actualizarProducto(1L, request);

        assertEquals("Actualizado", result.getNombre());
        assertEquals("OriginalCat", result.getCategoria());
        assertEquals(BigDecimal.valueOf(50), result.getPrecio());
    }

    @Test
    void actualizarProducto_ShouldThrowWhenNotFound() {
        when(productoRepository.findByIdAndEstadoProductoNot(99L, EstadoProducto.ELIMINADO))
                .thenReturn(Optional.empty());

        ProductoRequestDto request = ProductoRequestDto.builder().nombre("X").build();

        assertThrows(ProductoNoEncontradoException.class,
                () -> productoService.actualizarProducto(99L, request));
    }

    @Test
    void borrarProducto_ShouldSetEliminado() {
        Producto existing = Producto.builder()
                .id(1L).nombre("P").precio(BigDecimal.TEN)
                .estadoProducto(EstadoProducto.ACTIVO).build();

        when(productoRepository.findByIdAndEstadoProductoNot(1L, EstadoProducto.ELIMINADO))
                .thenReturn(Optional.of(existing));

        productoService.borrarProducto(1L);

        assertEquals(EstadoProducto.ELIMINADO, existing.getEstadoProducto());
        verify(productoRepository).save(existing);
    }

    @Test
    void borrarProducto_ShouldThrowWhenNotFound() {
        when(productoRepository.findByIdAndEstadoProductoNot(99L, EstadoProducto.ELIMINADO))
                .thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class,
                () -> productoService.borrarProducto(99L));
    }
}
