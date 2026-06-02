package com.superrrr.franky.service;

import com.superrrr.franky.dto.VentaRequestDto;
import com.superrrr.franky.dto.VentaResponseDto;
import com.superrrr.franky.enums.EstadoProducto;
import com.superrrr.franky.enums.EstadoSucursal;
import com.superrrr.franky.enums.EstadoVenta;
import com.superrrr.franky.exception.ProductoNoEncontradoException;
import com.superrrr.franky.exception.SucursalNoEncontradoException;
import com.superrrr.franky.mapper.VentaMapper;
import com.superrrr.franky.model.DetalleVenta;
import com.superrrr.franky.model.Producto;
import com.superrrr.franky.model.Sucursal;
import com.superrrr.franky.model.Venta;
import com.superrrr.franky.repositories.DetalleVentaRepository;
import com.superrrr.franky.repositories.ProductoRepository;
import com.superrrr.franky.repositories.SucursalRepository;
import com.superrrr.franky.repositories.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
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

    @Transactional
    public VentaResponseDto CrearVenta(VentaRequestDto ventaRequestDto){
        Sucursal sucursal = sucursalRepository.findByIdAndEstadoSucursalNot(ventaRequestDto.getSucursalId(), EstadoSucursal.ELIMINADO)
                .orElseThrow(
                        () -> new SucursalNoEncontradoException("Sucursal de la venta no encontrada, ID: ".concat(ventaRequestDto.getSucursalId().toString()))
                );

        Venta venta = new Venta();
        venta.setSucursal(sucursal);
        venta.setEstadoVenta(EstadoVenta.ACTIVO);
        Venta ventaSaved = ventaRepository.save(venta);

        List<DetalleVenta> detalleVentaList = new ArrayList<>();

        ventaRequestDto.getDetalle().stream()
                .forEach(detalleVentaRequestDto -> {
                    Producto producto = productoRepository.findByIdAndEstadoProductoNot(detalleVentaRequestDto.getProductoId(), EstadoProducto.ELIMINADO)
                            .orElseThrow(
                                    () -> new ProductoNoEncontradoException("Producto en la lista de venta no encontrada: ID ".concat(detalleVentaRequestDto.getProductoId().toString()))
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
}
