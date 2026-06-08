package com.superrrr.franky.venta.service;

import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import com.superrrr.franky.producto.exception.ProductoNoEncontradoException;
import com.superrrr.franky.producto.repositories.ProductoRepository;
import com.superrrr.franky.sucursal.entity.Sucursal;
import com.superrrr.franky.sucursal.enums.EstadoSucursal;
import com.superrrr.franky.sucursal.exception.SucursalNoEncontradoException;
import com.superrrr.franky.sucursal.repositories.SucursalRepository;
import com.superrrr.franky.venta.dto.DetalleVentaRequestDto;
import com.superrrr.franky.venta.dto.VentaRequestDto;
import com.superrrr.franky.venta.dto.VentaResponseDto;
import com.superrrr.franky.venta.entity.DetalleVenta;
import com.superrrr.franky.venta.entity.Venta;
import com.superrrr.franky.venta.enums.EstadoVenta;
import com.superrrr.franky.venta.exception.IdempotencyKeyRequeridaException;
import com.superrrr.franky.venta.exception.VentaNoEncontradaException;
import com.superrrr.franky.venta.repositories.DetalleVentaRepository;
import com.superrrr.franky.venta.repositories.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void CrearVenta_ShouldCreateVentaWithDetalles() {
        Sucursal sucursal = Sucursal.builder().id(1L).nombre("Suc Test").build();
        Producto producto = Producto.builder()
                .id(10L).nombre("Coca").precio(BigDecimal.valueOf(2.5))
                .build();
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(10L, 3)));

        Venta savedVenta = Venta.builder().id(1L).sucursal(sucursal).estadoVenta(EstadoVenta.ACTIVO).build();

        when(sucursalRepository.findByIdAndEstadoSucursalNot(1L, EstadoSucursal.ELIMINADO))
                .thenReturn(Optional.of(sucursal));
        when(ventaRepository.save(any(Venta.class))).thenReturn(savedVenta);
        when(productoRepository.findByIdAndEstadoProductoNot(10L, EstadoProducto.ELIMINADO))
                .thenReturn(Optional.of(producto));
        when(detalleVentaRepository.saveAll(anyList())).thenAnswer(i -> i.getArgument(0));

        when(ventaRepository.findByIdempotencyKey("test-key")).thenReturn(Optional.empty());

        VentaResponseDto result = ventaService.CrearVenta(request, "test-key");

        assertNotNull(result);
        assertEquals(1L, result.getSucursalId());
        verify(ventaRepository).save(any(Venta.class));
        verify(detalleVentaRepository).saveAll(anyList());
    }

    @Test
    void CrearVenta_ShouldThrowWhenSucursalNotFound() {
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(99L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(10L, 1)));

        when(sucursalRepository.findByIdAndEstadoSucursalNot(99L, EstadoSucursal.ELIMINADO))
                .thenReturn(Optional.empty());

        assertThrows(SucursalNoEncontradoException.class, () -> ventaService.CrearVenta(request, "test-key"));
    }

    @Test
    void CrearVenta_ShouldThrowWhenProductoNotFound() {
        Sucursal sucursal = Sucursal.builder().id(1L).build();
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(99L, 1)));

        when(sucursalRepository.findByIdAndEstadoSucursalNot(1L, EstadoSucursal.ELIMINADO))
                .thenReturn(Optional.of(sucursal));
        when(ventaRepository.save(any(Venta.class))).thenReturn(Venta.builder().id(1L).build());
        when(productoRepository.findByIdAndEstadoProductoNot(99L, EstadoProducto.ELIMINADO))
                .thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class, () -> ventaService.CrearVenta(request, "test-key"));
    }

    @Test
    void CrearVenta_WithIdempotencyKey_ShouldCreateOnFirstRequest() {
        Sucursal sucursal = Sucursal.builder().id(1L).build();
        Producto producto = Producto.builder().id(10L).nombre("Coca").precio(BigDecimal.TEN).build();
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(10L, 2)));

        Venta savedVenta = Venta.builder().id(1L).sucursal(sucursal).estadoVenta(EstadoVenta.ACTIVO)
                .idempotencyKey("key-123").build();

        when(sucursalRepository.findByIdAndEstadoSucursalNot(1L, EstadoSucursal.ELIMINADO))
                .thenReturn(Optional.of(sucursal));
        when(ventaRepository.findByIdempotencyKey("key-123")).thenReturn(Optional.empty());
        when(ventaRepository.save(any(Venta.class))).thenReturn(savedVenta);
        when(productoRepository.findByIdAndEstadoProductoNot(10L, EstadoProducto.ELIMINADO))
                .thenReturn(Optional.of(producto));
        when(detalleVentaRepository.saveAll(anyList())).thenAnswer(i -> i.getArgument(0));

        VentaResponseDto result = ventaService.CrearVenta(request, "key-123");

        assertNotNull(result);
        verify(ventaRepository).save(argThat(v -> "key-123".equals(v.getIdempotencyKey())));
    }

    @Test
    void CrearVenta_WithIdempotencyKey_ShouldReturnExistingOnRetry() {
        Sucursal sucursal = Sucursal.builder().id(1L).build();
        Venta existingVenta = Venta.builder().id(1L).sucursal(sucursal).estadoVenta(EstadoVenta.ACTIVO)
                .fecha(Instant.now()).idempotencyKey("key-123").build();
        DetalleVenta detalle = DetalleVenta.builder()
                .id(1L)
                .venta(existingVenta)
                .producto(Producto.builder().id(10L).build())
                .cantidad(2)
                .precioUnitario(BigDecimal.TEN)
                .subtotal(BigDecimal.valueOf(20))
                .build();
        existingVenta.setDetalles(List.of(detalle));
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(10L, 2)));

        when(ventaRepository.findByIdempotencyKey("key-123")).thenReturn(Optional.of(existingVenta));

        VentaResponseDto result = ventaService.CrearVenta(request, "key-123");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(ventaRepository, never()).save(any(Venta.class));
    }

    @Test
    void CrearVenta_WithoutIdempotencyKey_ShouldThrowException() {
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(10L, 2)));

        assertThrows(IdempotencyKeyRequeridaException.class, () -> ventaService.CrearVenta(request, null));
    }

    @Test
    void CrearVenta_WithBlankIdempotencyKey_ShouldThrowException() {
        VentaRequestDto request = new VentaRequestDto();
        request.setSucursalId(1L);
        request.setDetalle(List.of(new DetalleVentaRequestDto(10L, 2)));

        assertThrows(IdempotencyKeyRequeridaException.class, () -> ventaService.CrearVenta(request, "   "));
    }

    @Test
    void existePorIdempotencyKey_ShouldReturnTrueWhenExists() {
        when(ventaRepository.findByIdempotencyKey("key-123")).thenReturn(Optional.of(Venta.builder().build()));

        assertTrue(ventaService.existePorIdempotencyKey("key-123"));
    }

    @Test
    void existePorIdempotencyKey_ShouldReturnFalseWhenNotExists() {
        when(ventaRepository.findByIdempotencyKey("key-123")).thenReturn(Optional.empty());

        assertFalse(ventaService.existePorIdempotencyKey("key-123"));
    }

    @Test
    void borrarVenta_ShouldSetEliminado() {
        Venta venta = Venta.builder().id(1L).estadoVenta(EstadoVenta.ACTIVO).build();

        when(ventaRepository.findByIdAndEstadoVentaNot(1L, EstadoVenta.ELIMINADO))
                .thenReturn(Optional.of(venta));

        ventaService.borrarVenta(1L);

        assertEquals(EstadoVenta.ELIMINADO, venta.getEstadoVenta());
        verify(ventaRepository).save(venta);
    }

    @Test
    void borrarVenta_ShouldThrowWhenNotFound() {
        when(ventaRepository.findByIdAndEstadoVentaNot(99L, EstadoVenta.ELIMINADO))
                .thenReturn(Optional.empty());

        assertThrows(VentaNoEncontradaException.class, () -> ventaService.borrarVenta(99L));
    }
}
