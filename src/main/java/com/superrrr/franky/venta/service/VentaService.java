package com.superrrr.franky.venta.service;

import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import com.superrrr.franky.producto.exception.ProductoNoEncontradoException;
import com.superrrr.franky.producto.repositories.ProductoRepository;
import com.superrrr.franky.sucursal.entity.Sucursal;
import com.superrrr.franky.sucursal.enums.EstadoSucursal;
import com.superrrr.franky.sucursal.exception.SucursalNoEncontradoException;
import com.superrrr.franky.sucursal.repositories.SucursalRepository;
import com.superrrr.franky.venta.dto.VentaRequestDto;
import com.superrrr.franky.venta.dto.VentaResponseDto;
import com.superrrr.franky.venta.entity.DetalleVenta;
import com.superrrr.franky.venta.enums.EstadoVenta;
import com.superrrr.franky.venta.entity.Venta;
import com.superrrr.franky.venta.exception.IdempotencyKeyRequeridaException;
import com.superrrr.franky.venta.exception.VentaNoEncontradaException;
import com.superrrr.franky.venta.mapper.VentaMapper;
import com.superrrr.franky.venta.repositories.DetalleVentaRepository;
import com.superrrr.franky.venta.repositories.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public boolean existePorIdempotencyKey(String idempotencyKey) {
        return ventaRepository.findByIdempotencyKey(idempotencyKey).isPresent();
    }

    @Transactional
    public VentaResponseDto CrearVenta(VentaRequestDto ventaRequestDto, String idempotencyKey){
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IdempotencyKeyRequeridaException("El header Idempotency-Key es requerido para crear una venta");
        }
        try {
            return ventaRepository.findByIdempotencyKey(idempotencyKey)
                    .map(VentaMapper::toDTO)
                    .orElseGet(() -> crearNuevaVenta(ventaRequestDto, idempotencyKey));
        } catch (DataIntegrityViolationException e) {
            return ventaRepository.findByIdempotencyKey(idempotencyKey)
                    .map(VentaMapper::toDTO)
                    .orElseThrow(() -> new RuntimeException("Error de concurrencia al crear venta con clave: " + idempotencyKey, e));
        }
    }

    private VentaResponseDto crearNuevaVenta(VentaRequestDto ventaRequestDto, String idempotencyKey){
        Sucursal sucursal = sucursalRepository.findByIdAndEstadoSucursalNot(ventaRequestDto.getSucursalId(), EstadoSucursal.ELIMINADO)
                .orElseThrow(
                        () -> new SucursalNoEncontradoException("Sucursal de la venta no encontrada, ID: ".concat(ventaRequestDto.getSucursalId().toString()))
                );

        Venta venta = new Venta();
        venta.setSucursal(sucursal);
        venta.setEstadoVenta(EstadoVenta.ACTIVO);
        venta.setIdempotencyKey(idempotencyKey);
        Venta ventaSaved = ventaRepository.save(venta);

        List<DetalleVenta> detalleVentaList = new ArrayList<>();

        ventaRequestDto.getDetalle().stream()
                .forEach(detalleVentaRequestDto -> {
                    Producto producto = productoRepository.findByIdAndEstadoProductoNot(detalleVentaRequestDto.getProductoId(), EstadoProducto.ELIMINADO)
                            .orElseThrow(
                                    () -> new ProductoNoEncontradoException("Producto en la lista de venta no encontrada: ID ".concat(String.valueOf(detalleVentaRequestDto.getProductoId())))
                            );
                    DetalleVenta detalleVenta = new DetalleVenta();
                    detalleVenta.setProducto(producto);
                    detalleVenta.setVenta(ventaSaved);
                    detalleVenta.setCantidad(detalleVentaRequestDto.getCantidad());
                    detalleVenta.setPrecioUnitario(producto.getPrecio());
                    detalleVenta.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(detalleVenta.getCantidad())));

                    detalleVentaList.add(detalleVenta);
                });
        detalleVentaRepository.saveAll(detalleVentaList);
        ventaSaved.setDetalles(detalleVentaList);
        return VentaMapper.toDTO(ventaSaved);
    }

    public List<VentaResponseDto> obtenerVentasPorSucursalYFecha(Long sucursalId, LocalDate fecha){
        Sucursal sucursal = Sucursal.builder().id(sucursalId).build();
        Instant fechaInicio = fecha.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant fechaFin = fecha.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        List<Venta> ventaList = ventaRepository.findByFechaBetweenAndSucursalAndEstadoVentaNot(fechaInicio, fechaFin, sucursal, EstadoVenta.ELIMINADO);

        return ventaList.stream().map(VentaMapper::toDTO).toList();
    }

    public void borrarVenta(Long ventaId){
        Venta venta = ventaRepository.findByIdAndEstadoVentaNot(ventaId, EstadoVenta.ELIMINADO)
                .orElseThrow(()-> new VentaNoEncontradaException("Venta no encontrada con el ID: ".concat(String.valueOf(ventaId))));

        venta.setEstadoVenta(EstadoVenta.ELIMINADO);
        ventaRepository.save(venta);
    }
}
