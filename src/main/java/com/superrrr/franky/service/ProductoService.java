package com.superrrr.franky.service;

import com.superrrr.franky.dto.ProductoRequestDto;
import com.superrrr.franky.dto.ProductoResponseDto;
import com.superrrr.franky.enums.EstadoProducto;
import com.superrrr.franky.mapper.ProductoMapper;
import com.superrrr.franky.model.Producto;
import com.superrrr.franky.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoResponseDto> obtenerListaProductos(){
        List<Producto> productos = productoRepository.findByEstadoProductoNot(EstadoProducto.ELIMINADO);
        return productos.stream().map(ProductoMapper::toDTO).toList();
    }

    public ProductoResponseDto crearProducto(ProductoRequestDto productoRequestDto){
        Producto producto = ProductoMapper.toModel(productoRequestDto);
        producto.setEstadoProducto(EstadoProducto.ACTIVO);
        Producto productoGuardado = productoRepository.save(producto);
        return ProductoMapper.toDTO(productoGuardado);
    }


}
