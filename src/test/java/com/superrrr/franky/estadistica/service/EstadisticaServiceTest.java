package com.superrrr.franky.estadistica.service;

import com.superrrr.franky.estadistica.dto.ProductoMasVendidoDto;
import com.superrrr.franky.estadistica.exception.EstadisticaNoEncontradaException;
import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.venta.entity.DetalleVenta;
import com.superrrr.franky.venta.entity.Venta;
import com.superrrr.franky.venta.enums.EstadoVenta;
import com.superrrr.franky.venta.repositories.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadisticaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private EstadisticaService estadisticaService;

    @Test
    void obtenerProductoMasVendido_ShouldReturnProductWithHighestTotal() {
        Producto cola = Producto.builder().id(1L).nombre("Coca Cola").categoria("Bebidas").precio(BigDecimal.valueOf(2.5)).build();
        Producto pan = Producto.builder().id(2L).nombre("Pan").categoria("Panaderia").precio(BigDecimal.valueOf(1.0)).build();

        Venta venta1 = Venta.builder().id(1L).estadoVenta(EstadoVenta.ACTIVO).build();
        Venta venta2 = Venta.builder().id(2L).estadoVenta(EstadoVenta.ACTIVO).build();

        DetalleVenta d1 = DetalleVenta.builder().id(1L).producto(cola).venta(venta1).cantidad(5).build();
        DetalleVenta d2 = DetalleVenta.builder().id(2L).producto(pan).venta(venta1).cantidad(2).build();
        DetalleVenta d3 = DetalleVenta.builder().id(3L).producto(cola).venta(venta2).cantidad(3).build();

        venta1.setDetalles(List.of(d1, d2));
        venta2.setDetalles(List.of(d3));

        when(ventaRepository.findAllByEstadoVentaNot(EstadoVenta.ELIMINADO))
                .thenReturn(List.of(venta1, venta2));

        ProductoMasVendidoDto result = estadisticaService.obtenerProductoMasVendido();

        assertEquals(1L, result.getId());
        assertEquals("Coca Cola", result.getNombre());
        assertEquals(8L, result.getTotalVendido());
    }

    @Test
    void obtenerProductoMasVendido_ShouldThrowWhenNoVentas() {
        when(ventaRepository.findAllByEstadoVentaNot(EstadoVenta.ELIMINADO))
                .thenReturn(List.of());

        assertThrows(EstadisticaNoEncontradaException.class,
                () -> estadisticaService.obtenerProductoMasVendido());
    }

    @Test
    void obtenerProductoMasVendido_ShouldThrowWhenVentasHaveNoDetalles() {
        Venta venta = Venta.builder().id(1L).estadoVenta(EstadoVenta.ACTIVO).detalles(List.of()).build();

        when(ventaRepository.findAllByEstadoVentaNot(EstadoVenta.ELIMINADO))
                .thenReturn(List.of(venta));

        assertThrows(EstadisticaNoEncontradaException.class,
                () -> estadisticaService.obtenerProductoMasVendido());
    }
}
