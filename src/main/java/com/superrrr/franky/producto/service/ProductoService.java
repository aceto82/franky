package com.superrrr.franky.producto.service;

import com.superrrr.franky.producto.dto.ProductoRequestDto;
import com.superrrr.franky.producto.dto.ProductoResponseDto;
import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import com.superrrr.franky.producto.exception.ProductoNoEncontradoException;
import com.superrrr.franky.producto.mapper.ProductoMapper;
import com.superrrr.franky.producto.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoResponseDto> obtenerProductos(){
        List<Producto> productos = productoRepository.findByEstadoProductoNot(EstadoProducto.ELIMINADO);
        return productos.stream().map(ProductoMapper::toDTO).toList();
    }

    public ProductoResponseDto crearProducto(ProductoRequestDto productoRequestDto){
        Producto producto = ProductoMapper.toModel(productoRequestDto);
        producto.setEstadoProducto(EstadoProducto.ACTIVO);
        Producto productoNuevo = productoRepository.save(producto);
        return ProductoMapper.toDTO(productoNuevo);
    }

    public ProductoResponseDto actualizarProducto(Long id, ProductoRequestDto productoRequestDto){
        Producto producto = productoRepository.findByIdAndEstadoProductoNot(id, EstadoProducto.ELIMINADO)
                .orElseThrow(()->new ProductoNoEncontradoException("Producto no encontrado con el ID: ".concat(String.valueOf(id))));

        if (Objects.nonNull(productoRequestDto.getNombre())){
            producto.setNombre(productoRequestDto.getNombre());
        }
        if (Objects.nonNull(productoRequestDto.getCategoria())){
            producto.setCategoria(productoRequestDto.getCategoria());
        }
        if (Objects.nonNull(productoRequestDto.getPrecio())){
            producto.setPrecio(productoRequestDto.getPrecio());
        }

        Producto productoActualizado = productoRepository.save(producto);
        return ProductoMapper.toDTO(productoActualizado);
    }

    public void borrarProducto(Long id){
        Producto producto = productoRepository.findByIdAndEstadoProductoNot(id, EstadoProducto.ELIMINADO)
                .orElseThrow(()->new ProductoNoEncontradoException("Producto no encontrado con el ID: ".concat(String.valueOf(id))));

        producto.setEstadoProducto(EstadoProducto.ELIMINADO);
        productoRepository.save(producto);
    }
}
