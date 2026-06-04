package com.superrrr.franky.estadistica.service;

import com.superrrr.franky.estadistica.dto.ProductoMasVendidoDto;
import com.superrrr.franky.estadistica.exception.EstadisticaNoEncontradaException;
import com.superrrr.franky.venta.entity.DetalleVenta;
import com.superrrr.franky.venta.entity.Venta;
import com.superrrr.franky.venta.enums.EstadoVenta;
import com.superrrr.franky.venta.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstadisticaService {

    @Autowired
    private VentaRepository ventaRepository;

    public ProductoMasVendidoDto obtenerProductoMasVendido() {
        List<Venta> ventas = ventaRepository.findAllByEstadoVentaNot(EstadoVenta.ELIMINADO);

        if (ventas.isEmpty()) {
            throw new EstadisticaNoEncontradaException("No hay ventas registradas");
        }

        Map<Long, Long> productoTotal = ventas.stream()
                .flatMap(venta -> venta.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        detalle -> detalle.getProducto().getId(),
                        Collectors.summingLong(DetalleVenta::getCantidad)
                ));

        Long productoId = productoTotal.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new EstadisticaNoEncontradaException("No hay ventas registradas"));

        DetalleVenta ejemplo = ventas.stream()
                .flatMap(venta -> venta.getDetalles().stream())
                .filter(detalle -> detalle.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new EstadisticaNoEncontradaException("No hay ventas registradas"));

        return ProductoMasVendidoDto.builder()
                .id(ejemplo.getProducto().getId())
                .nombre(ejemplo.getProducto().getNombre())
                .categoria(ejemplo.getProducto().getCategoria())
                .precio(ejemplo.getProducto().getPrecio())
                .totalVendido(productoTotal.get(productoId))
                .build();
    }
}
